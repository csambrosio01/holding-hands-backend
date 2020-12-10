package com.usp.holdinghands.repositories

import com.usp.holdinghands.models.Match
import com.usp.holdinghands.models.MatchStatus
import com.usp.holdinghands.models.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface MatchRepository : CrudRepository<Match, Long> {
    fun findAllByStatusAndUserReceived(status: MatchStatus, userReceived: User): List<Match>
    fun findAllByUserReceivedAndStatusIn(userReceived: User, status: List<MatchStatus>): MutableList<Match>
    fun findAllByUserSent(userSent: User): MutableList<Match>
    @Query("SELECT m FROM Match m WHERE m.status = :status AND (m.userReceived = :user OR m.userSent = :user)")
    fun findAllByStatusAndUser(status: MatchStatus, user: User): List<Match>
}
