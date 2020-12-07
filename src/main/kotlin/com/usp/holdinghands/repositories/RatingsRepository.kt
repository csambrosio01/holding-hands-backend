package com.usp.holdinghands.repositories

import com.usp.holdinghands.models.Ratings
import com.usp.holdinghands.models.Reports
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface RatingsRepository : CrudRepository<Ratings, Long> {
    @Query("SELECT rating FROM Ratings r WHERE r.user_rated = :userId")
    fun ratingAverage(@Param("userId") userId: Long?): Double
}
