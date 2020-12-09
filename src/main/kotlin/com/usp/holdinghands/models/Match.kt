package com.usp.holdinghands.models

import javax.persistence.*

enum class MatchStatus {
    PENDING, ACCEPT, DONE, REJECT, HISTORY
}

@Entity
@Table(name = "match")
class Match(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @JoinColumn(name = "match_id") var matchId: Long? = null,
    @ManyToOne @JoinColumn(name = "user_sent") var userSent: User,
    @ManyToOne @JoinColumn(name = "user_received") var userReceived: User,
    @Enumerated(EnumType.STRING) @Column(columnDefinition = "VARCHAR NOT NULL DEFAULT 'PENDING'") var status: MatchStatus = MatchStatus.PENDING
)
