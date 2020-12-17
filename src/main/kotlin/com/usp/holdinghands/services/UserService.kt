package com.usp.holdinghands.services

import com.usp.holdinghands.models.*
import com.usp.holdinghands.models.dtos.*
import org.springframework.security.core.Authentication

interface UserService {
    fun createUser(userRequest: UserDTO): Login
    fun loadUserByCredentials(login: LoginDTO): Login
    fun getLoggedUser(auth: Authentication, coordinates: CoordinatesDTO? = null): User
    fun getUsers(coordinates: CoordinatesDTO,
                 authentication: Authentication,
                 maxDistance: Double,
                 gender: Gender,
                 ageMin: Int,
                 ageMax: Int,
                 helpNumberMin: Int,
                 helpNumberMax: Int,
                 helpTypes: List<HelpType>?): List<User>
    fun updateIsHelper(authentication: Authentication): User
    fun reportUser(reportRequest: ReportsDTO, authentication: Authentication): Reports
    fun rateUser(ratingRequest: RatingsDTO, authentication: Authentication): Double
    fun calculateUsersDistance(user1: User, user2: User): Double
    fun getAge(user: User): Int
    fun getUserById(authentication: Authentication, userId: Long): User
    fun getRateUser(authentication: Authentication, userId: Long): Double
    fun updatePhoneAvailability(authentication: Authentication): User
}
