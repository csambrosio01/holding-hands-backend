package com.usp.holdinghands.services

import com.usp.holdinghands.models.*
import com.usp.holdinghands.models.dtos.CoordinatesDTO
import com.usp.holdinghands.models.dtos.LoginDTO
import com.usp.holdinghands.models.dtos.ReportsDTO
import com.usp.holdinghands.models.dtos.UserDTO
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
    fun reportUser(report: ReportsDTO, authentication: Authentication): Reports
}
