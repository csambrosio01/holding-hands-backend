package com.usp.holdinghands.repositories

import com.usp.holdinghands.models.Ratings
import com.usp.holdinghands.models.Reports
import com.usp.holdinghands.models.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface RatingsRepository : CrudRepository<Ratings, Long> {
    @Query("SELECT avg(rating) FROM Ratings r WHERE r.userRated.userId = :userId")
    fun ratingAverage(@Param("userId") userId: Long?): Double
    fun findByUserRatedAndUserReviewer(userReviewer: User, userRated: User): Ratings?
}
