package com.usp.holdinghands.services

import com.usp.holdinghands.models.User
import com.usp.holdinghands.models.UserRequest

interface UserService {
    fun createUser(userRequest: UserRequest): User
    fun getUsers(): List<User>
}
