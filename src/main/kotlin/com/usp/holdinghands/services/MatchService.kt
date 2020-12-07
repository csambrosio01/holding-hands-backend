package com.usp.holdinghands.services

import com.usp.holdinghands.models.Match
import com.usp.holdinghands.models.MatchStatus
import org.springframework.security.core.Authentication

interface MatchService {
    fun sendInvite(authentication: Authentication, userIdReceived: Long): Match
    fun acceptRejectInvite(authentication: Authentication, matchId: Long, status: MatchStatus): Match
    fun getPendingHistory(authentication: Authentication, status: MatchStatus): List<Match>
}
