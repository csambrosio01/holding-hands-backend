package com.usp.holdinghands.models

import java.io.Serializable
import javax.persistence.*


class ReportsPrimaryKey : Serializable {
    private val user_reporter = 0
    private val user_reported = 0
}
@Entity
@Table(name = "reports")
@IdClass(ReportsPrimaryKey::class)
class Reports (@Id @ManyToOne
               @JoinColumn(name = "user_reporter") var user_reporter: User,
               @Id @ManyToOne
               @JoinColumn(name = "user_reported") var user_reported: User,
               var message: String): Serializable
