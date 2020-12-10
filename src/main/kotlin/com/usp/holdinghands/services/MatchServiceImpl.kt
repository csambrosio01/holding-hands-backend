package com.usp.holdinghands.services

import com.usp.holdinghands.models.Match
import com.usp.holdinghands.models.MatchStatus
import com.usp.holdinghands.models.User
import com.usp.holdinghands.repositories.MatchRepository
import com.usp.holdinghands.repositories.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class MatchServiceImpl(
    private val matchRepository: MatchRepository,
    private val userService: UserService,
    private val userRepository: UserRepository
) :
    MatchService {

    override fun sendInvite(authentication: Authentication, userIdReceived: Long): Match {
        val userSent = userService.getLoggedUser(authentication)
        val userReceived = userRepository.findById(userIdReceived).orElseThrow()
        checkInviteConstraints(userSent, userReceived)
        val match = Match(
            matchId = null,
            userSent = userSent,
            userReceived = userReceived,
            status = MatchStatus.PENDING
        )
        return matchRepository.save(match)
    }

    private fun checkInviteConstraints(userSent: User, userReceived: User) {
        if (userSent.userId != null && userReceived.userId != null && userSent.userId != userReceived.userId && userSent.isHelper != userReceived.isHelper) {
            return
        } else {
            throw DataIntegrityViolationException("Match constraint invalid")
        }
    }

    override fun acceptRejectInvite(authentication: Authentication, matchId: Long, status: MatchStatus): Match {
        val match = matchRepository.findById(matchId).orElseThrow()
        val userReceived = userService.getLoggedUser(authentication)
        checkAcceptRejectConstraints(userReceived, match)
        match.status = status
        if (status == MatchStatus.ACCEPT) {
            userRepository.save(
                if (userReceived.isHelper) {
                    userReceived.numberOfHelps += 1
                    userReceived
                } else {
                    match.userSent.numberOfHelps += 1
                    match.userSent
                }
            )
        }
        return matchRepository.save(match)
    }

    private fun checkAcceptRejectConstraints(userReceived: User, match: Match) {
        if (userReceived.userId != null && userReceived.userId == match.userReceived.userId && match.status == MatchStatus.PENDING) {
            return
        } else {
            throw DataIntegrityViolationException("Match constraint invalid")
        }
    }

    override fun getPendingHistory(authentication: Authentication, status: MatchStatus): List<Match> {
        val user = userService.getLoggedUser(authentication)
        return if (status == MatchStatus.PENDING) {
            matchRepository.findAllByStatusAndUserReceived((MatchStatus.PENDING), user)
                .sortedByDescending { it.matchId }
                .map {
                    userService.calculateUsersDistance(user, it.userReceived)
                    userService.calculateUsersDistance(user, it.userSent)

                    userService.getAge(it.userReceived)
                    userService.getAge(it.userSent)

                    it
                }
        } else {
            val matchList = matchRepository.findAllByUserReceivedAndStatusIn(
                user,
                mutableListOf(MatchStatus.ACCEPT, MatchStatus.DONE, MatchStatus.REJECT)
            )
            matchList.addAll(matchRepository.findAllByUserSent(user))
            matchList.sortedByDescending { it.matchId }
                .map {
                    userService.calculateUsersDistance(user, it.userReceived)
                    userService.calculateUsersDistance(user, it.userSent)

                    userService.getAge(it.userReceived)
                    userService.getAge(it.userSent)

                    it
                }
        }
    }
}
