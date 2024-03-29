package com.usp.holdinghands.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import javax.persistence.*


class RatingsPrimaryKey : Serializable {
    private val userReviewer: Long = 0L
    private val userRated: Long = 0L
}

@Entity
@Table(name = "ratings")
@IdClass(RatingsPrimaryKey::class)
class Ratings(@Id @ManyToOne
              @JsonIgnore @JoinColumn(name = "user_reviewer") var userReviewer: User,
              @Id @ManyToOne
              @JsonIgnore @JoinColumn(name = "user_rated") var userRated: User,
              var rating: Double) : Serializable
