package com.usp.holdinghands.services

import com.usp.holdinghands.exceptions.UserBlockedException
import com.usp.holdinghands.exceptions.UserNotFoundException
import com.usp.holdinghands.exceptions.WrongCredentialsException
import com.usp.holdinghands.models.*
import com.usp.holdinghands.models.dtos.*
import com.usp.holdinghands.repositories.RatingsRepository
import com.usp.holdinghands.repositories.ReportsRepository
import com.usp.holdinghands.repositories.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.*
import java.util.*

@Service
class UserServiceImpl(
        private val haversineService: HaversineService,
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val reportsRepository: ReportsRepository,
        private val ratingsRepository: RatingsRepository
) : UserService {

    override fun createUser(userRequest: UserDTO): Login {
        val user = User(
                name = userRequest.name,
                helpTypes = convertToDatabaseColumn(userRequest.helpTypes),
                age = 0,
                distance = 0.0,
                gender = userRequest.gender,
                profession = userRequest.profession,
                email = userRequest.email,
                password = passwordEncoder.encode(userRequest.password),
                phone = userRequest.phone,
                isHelper = userRequest.isHelper,
                birth = userRequest.birth,
                latitude = userRequest.latitude,
                longitude = userRequest.longitude,
                blocked = false
        )
        val token = generateJWTToken(user.email)
        user.age = getAge(user)
        return Login(userRepository.save(user), token)
    }

    override fun loadUserByCredentials(login: LoginDTO): Login {
        val user = userRepository.findByEmail(login.email) ?: throw UserNotFoundException()
        user.age = getAge(user)
        if (passwordEncoder.matches(login.password, user.password)) {
            if (user.blocked) throw UserBlockedException()
            val token = generateJWTToken(login.email)
            return Login(user, token)
        } else {
            throw WrongCredentialsException()
        }
    }

    override fun getLoggedUser(auth: Authentication, coordinates: CoordinatesDTO?): User {
        val username = auth.name
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        if (coordinates != null) {
            user.latitude = coordinates.latitude
            user.longitude = coordinates.longitude
            userRepository.save(user)
        }

        return user
    }

    override fun getUsers(coordinates: CoordinatesDTO, authentication: Authentication,
                          maxDistance: Double,
                          gender: Gender,
                          ageMin: Int,
                          ageMax: Int,
                          helpNumberMin: Int,
                          helpNumberMax: Int,
                          helpTypes: List<HelpType>?): List<User> {
        val user = getLoggedUser(authentication, coordinates)
        val usersList = userRepository.findByBlockedAndIsHelper(false, !user.isHelper)
        var usersListFiltered = usersList.filter { calculateUsersDistance(user, it) <= maxDistance && (getAge(it) in ageMin..ageMax) }
        if (gender != Gender.BOTH) {
            usersListFiltered = usersListFiltered.filter { it.gender == gender }
        }
        if (helpTypes != null && helpTypes.isNotEmpty()) {
            usersListFiltered = usersListFiltered.filter { ListHelpTypesConverter.convertToEntityAttribute(it.helpTypes).any { helpType -> helpType in helpTypes } }
        }
        return usersListFiltered.sortedBy { it.distance }

    }

    override fun reportUser(reportRequest: ReportsDTO, authentication: Authentication): Reports {
        val user = getLoggedUser(authentication)
        val userReportedOptional = userRepository.findById(reportRequest.userReported)
        val userReported = userReportedOptional.get()
        val report = Reports(
                userReporter = user,
                userReported = userReported,
                message = reportRequest.message
        )
        if (reportsRepository.existsByUserReporterAndUserReported(report.userReporter, report.userReported)) throw DataIntegrityViolationException("Duplicate value")
        reportsRepository.save(report)
        checkUserReportsNumber(userReported)
        return report
    }

    private fun checkUserReportsNumber(user: User) {
        val reportsNumber = reportsRepository.findByUserReported(user).size
        if (reportsNumber >= 3) {
            user.blocked = true
            userRepository.save(user)
        }
    }

    override fun rateUser(ratingRequest: RatingsDTO, authentication: Authentication): Double {
        val user = getLoggedUser(authentication)
        val userRated = userRepository.findById(ratingRequest.userRated).orElseThrow()
        val rating = Ratings (
            userReviewer = user,
            userRated = userRated,
            rating = ratingRequest.rating
        )
        ratingsRepository.save(rating)
        userRated.rating = ratingsRepository.ratingAverage(userRated.userId)
        userRepository.save(userRated)
        return userRated.rating
    }

    override fun updateIsHelper(authentication: Authentication): User {
        val user = getLoggedUser(authentication)
        user.isHelper = !user.isHelper
        return userRepository.save(user)
    }

    override fun getAge(user: User): Int {
        val year = user.birth.get(Calendar.YEAR)
        val month = user.birth.get(Calendar.MONTH)
        val day = user.birth.get(Calendar.DAY_OF_MONTH)
        user.age = Period.between(LocalDate.of(year, month, day), LocalDate.now()).years
        return user.age
    }

    override fun calculateUsersDistance(user1: User, user2: User): Double {
        user2.distance = haversineService.haversine(user1.latitude, user1.longitude, user2.latitude, user2.longitude)
        return user2.distance
    }

    private fun convertToDatabaseColumn(attribute: List<HelpType>?): String? {
        if (attribute != null && attribute.isNotEmpty()) {
            var value = ""
            for (helpType in attribute) value += helpType.name + ","
            return value.dropLast(1)
        }
        return null
    }

    private fun generateJWTToken(email: String): String {
        val secretKey = "mySecretKey"

        val token = Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(null)
                .signWith(SignatureAlgorithm.HS512, secretKey.toByteArray()).compact()

        return "Bearer $token"
    }
}
