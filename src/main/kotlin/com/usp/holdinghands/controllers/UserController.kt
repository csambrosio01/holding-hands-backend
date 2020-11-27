package com.usp.holdinghands.controllers

import com.usp.holdinghands.models.User
import com.usp.holdinghands.models.UserRequest
import com.usp.holdinghands.services.UserService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/user")
class UserController(val userService: UserService) {

    @PostMapping("/create")
    fun createUser(@RequestBody user: UserRequest): ResponseEntity<Any> {
        return try {
            ResponseEntity(userService.createUser(user), HttpStatus.OK)
        } catch (e: DataIntegrityViolationException) {
            ResponseEntity("Duplicate value", HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @GetMapping
    fun getUsers(): ResponseEntity<Any>{
        return try {
            ResponseEntity(userService.getUsers(), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity("Users not found", HttpStatus.NOT_FOUND)
        }

    }
}
