package com.usp.holdinghands.controllers

import com.usp.holdinghands.exceptions.UserNotFoundException
import com.usp.holdinghands.exceptions.WrongCredentialsException
import com.usp.holdinghands.models.dtos.CoordinatesDTO
import com.usp.holdinghands.models.dtos.LoginDTO
import com.usp.holdinghands.models.dtos.UserDTO
import com.usp.holdinghands.services.UserService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(val userService: UserService) {

    @PostMapping("/create")
    fun createUser(@RequestBody user: UserDTO): ResponseEntity<Any> {
        return try {
            ResponseEntity(userService.createUser(user), HttpStatus.OK)
        } catch (e: DataIntegrityViolationException) {
            ResponseEntity("Duplicate value", HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping
    fun getUsers(@RequestBody coordinates: CoordinatesDTO): ResponseEntity<Any> {
        val authentication = SecurityContextHolder.getContext().authentication;
        return try {
            ResponseEntity(userService.getUsers(coordinates, authentication), HttpStatus.OK)
        } catch (e: UserNotFoundException) {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody login: LoginDTO): ResponseEntity<Any> {
        return try {
            val a = userService.loadUserByCredentials(login)
            ResponseEntity(a, HttpStatus.OK)
        } catch (e: UserNotFoundException) {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        } catch (e: WrongCredentialsException) {
            ResponseEntity("Invalid credentials", HttpStatus.BAD_REQUEST)
        }
    }
}
