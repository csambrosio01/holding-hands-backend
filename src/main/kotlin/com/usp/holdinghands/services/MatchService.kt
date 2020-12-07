package com.usp.holdinghands.services

import com.usp.holdinghands.models.Match
import org.springframework.security.core.Authentication

interface MatchService {
    fun sendInvite(authentication: Authentication, userIdReceived: Long): Match
}
