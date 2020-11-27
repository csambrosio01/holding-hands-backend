package com.usp.holdinghands.services

import com.usp.holdinghands.models.User
import com.usp.holdinghands.models.dtos.UserDTO

interface UserService {
    fun createUser(userRequest: UserDTO): User
    fun getUsers(): List<User>
}
