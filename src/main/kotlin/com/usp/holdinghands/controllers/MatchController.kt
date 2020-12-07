package com.usp.holdinghands.controllers

import com.usp.holdinghands.exceptions.UserNotFoundException
import com.usp.holdinghands.models.MatchStatus
import com.usp.holdinghands.services.MatchService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*

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

    @PostMapping("/{status}/{match_id_sent}")
    fun acceptRejectInvitation(
        @PathVariable("status") status: String,
        @PathVariable("match_id_sent") matchId: Long
    ): ResponseEntity<Any> {
        val authentication = SecurityContextHolder.getContext().authentication
        return try {
            ResponseEntity(matchService.acceptRejectInvite(authentication, matchId, MatchStatus.valueOf(status.toUpperCase())), HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        } catch (e: UserNotFoundException) {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        } catch (e: DataIntegrityViolationException) {
            ResponseEntity(e.localizedMessage, HttpStatus.CONFLICT)
        }
    }

    @GetMapping("/{status}")
    fun getPendingHistory(@PathVariable("status") status: String): ResponseEntity<Any> {
        val authentication = SecurityContextHolder.getContext().authentication
        return try {
            ResponseEntity(matchService.getPendingHistory(authentication, MatchStatus.valueOf(status.toUpperCase())), HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        } catch (e: UserNotFoundException) {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        } catch (e: DataIntegrityViolationException) {
            ResponseEntity(e.localizedMessage, HttpStatus.CONFLICT)
        }
    }
}
