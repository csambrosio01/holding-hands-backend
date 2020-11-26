package com.usp.holdinghands.repositories

import com.usp.holdinghands.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): User?
    override fun findAll(): List<User>
}
