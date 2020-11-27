package com.usp.holdinghands.services

import com.usp.holdinghands.exceptions.UserNotFoundException
import com.usp.holdinghands.exceptions.WrongCredentialsException
import com.usp.holdinghands.models.HelpType
import com.usp.holdinghands.models.Login
import com.usp.holdinghands.models.User
import com.usp.holdinghands.models.dtos.LoginDTO
import com.usp.holdinghands.models.dtos.UserDTO
import com.usp.holdinghands.repositories.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun createUser(userRequest: UserDTO): Login {
        val user = User(
                name = userRequest.name,
                helpTypes = convertToDatabaseColumn(userRequest.helpTypes),
                gender = userRequest.gender,
                profession = userRequest.profession,
                email = userRequest.email,
                password = passwordEncoder.encode(userRequest.password),
                phone = userRequest.phone,
                isHelper = userRequest.isHelper,
                birth = userRequest.birth,
                address = userRequest.address
        )

        val token = generateJWTToken(user.email)
        return Login(userRepository.save(user), token)
    }

    override fun loadUserByCredentials(login: LoginDTO): Login {
        val user = userRepository.findByEmail(login.email) ?: throw UserNotFoundException()

        if (passwordEncoder.matches(login.password, user.password)) {
            val token = generateJWTToken(login.email)
            return Login(user, token)
        } else {
            throw WrongCredentialsException()
        }
    }

    override fun getUsers(): List<User> {
        return userRepository.findAll()
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
