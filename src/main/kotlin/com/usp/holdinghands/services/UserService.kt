package com.usp.holdinghands.services

import com.usp.holdinghands.models.Login
import com.usp.holdinghands.models.User
import com.usp.holdinghands.models.dtos.LoginDTO
import com.usp.holdinghands.models.dtos.UserDTO

interface UserService {
    fun createUser(userRequest: UserDTO): Login
    fun loadUserByCredentials(login: LoginDTO): Login
    fun getUsers(): List<User>
}
