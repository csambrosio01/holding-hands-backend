package com.usp.holdinghands.models

import java.io.Serializable
import java.util.*
import javax.persistence.*


class ReportsPrimaryKey : Serializable {
    private val userReporter: Long = 0L
    private val userReported: Long = 0L
}
@Entity
@Table(name = "reports")
@IdClass(ReportsPrimaryKey::class)
class Reports(@Id @ManyToOne
               @JoinColumn(name = "user_reporter") var userReporter: User,
              @Id @ManyToOne
               @JoinColumn(name = "user_reported") var userReported: User,
              var message: String): Serializable
