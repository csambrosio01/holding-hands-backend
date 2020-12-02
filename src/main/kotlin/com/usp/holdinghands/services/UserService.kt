package com.usp.holdinghands.services

import com.usp.holdinghands.models.Login
import com.usp.holdinghands.models.User
import com.usp.holdinghands.models.dtos.CoordinatesDTO
import com.usp.holdinghands.models.dtos.LoginDTO
import com.usp.holdinghands.models.dtos.UserDTO
import org.springframework.security.core.Authentication

interface UserService {
    fun createUser(userRequest: UserDTO): Login
    fun loadUserByCredentials(login: LoginDTO): Login
    fun getUsers(coordinates: CoordinatesDTO, authentication: Authentication, distance: Double): List<User>
}
