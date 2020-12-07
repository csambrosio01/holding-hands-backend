package com.usp.holdinghands.controllers

import com.usp.holdinghands.exceptions.UserNotFoundException
import com.usp.holdinghands.services.MatchService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/match")
class MatchController(val matchService: MatchService) {

    @PostMapping("/invite/{user_id_received}")
    fun sendInvitation(@PathVariable("user_id_received") userIdReceived: Long): ResponseEntity<Any> {
        val authentication = SecurityContextHolder.getContext().authentication
        return try {
            ResponseEntity(matchService.sendInvite(authentication, userIdReceived), HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        } catch (e: UserNotFoundException) {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        } catch (e: DataIntegrityViolationException) {
            ResponseEntity(e.localizedMessage, HttpStatus.CONFLICT)
        }
    }
}
