package com.usp.holdinghands.repositories

import com.usp.holdinghands.models.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): User?
    override fun findAll(): List<User>
    fun findByIsHelper(isHelper: Boolean): List<User>
}
