package com.usp.holdinghands.controllers

import com.usp.holdinghands.exceptions.UserBlockedException
import com.usp.holdinghands.exceptions.UserNotFoundException
import com.usp.holdinghands.exceptions.WrongCredentialsException
import com.usp.holdinghands.models.Gender
import com.usp.holdinghands.models.ListHelpTypesConverter
import com.usp.holdinghands.models.dtos.*
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
    fun getUsers(@RequestBody coordinates: CoordinatesDTO,
                 @RequestParam(defaultValue = "30.0") distance: Double,
                 @RequestParam(defaultValue = "BOTH") gender: Gender,
                 @RequestParam(defaultValue = "18") ageMin: Int,
                 @RequestParam(defaultValue = "25") ageMax: Int,
                 @RequestParam(defaultValue = "0") helpNumberMin: Int,
                 @RequestParam(defaultValue = "50") helpNumberMax: Int,
                 @RequestParam(required = false) helpTypes: String?): ResponseEntity<Any> {
        val authentication = SecurityContextHolder.getContext().authentication
        val listHelpTypes = ListHelpTypesConverter.convertToEntityAttribute(helpTypes)
        return try {
            ResponseEntity(userService.getUsers(coordinates, authentication, distance, gender, ageMin, ageMax, helpNumberMin, helpNumberMax, listHelpTypes), HttpStatus.OK)
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
        } catch (e: UserBlockedException) {
            ResponseEntity("Blocked User", HttpStatus.FORBIDDEN)
        }
    }

    @PostMapping("/report")
    fun report(@RequestBody reportRequest: ReportsDTO): ResponseEntity<Any> {
        val authentication = SecurityContextHolder.getContext().authentication
        return try {
            ResponseEntity(userService.reportUser(reportRequest, authentication), HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        } catch (e: DataIntegrityViolationException) {
            ResponseEntity("User already reported", HttpStatus.CONFLICT)
        } catch (e: UserNotFoundException) {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/rate")
    fun rate(@RequestBody ratingRequest: RatingsDTO): ResponseEntity<Any> {
        val authentication = SecurityContextHolder.getContext().authentication
        return try {
            ResponseEntity(userService.rateUser(ratingRequest, authentication), HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        } catch (e: UserNotFoundException) {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/update")
    fun update(): ResponseEntity<Any> {
        val authentication = SecurityContextHolder.getContext().authentication
        return try {
            ResponseEntity(userService.updateIsHelper(authentication), HttpStatus.OK)
        } catch (e: UserNotFoundException) {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        }
    }
}
