package com.usp.holdinghands.repositories

import com.usp.holdinghands.models.Reports
import com.usp.holdinghands.models.User
import org.springframework.data.repository.CrudRepository

interface ReportsRepository: CrudRepository<Reports, Long> {
    fun existsByUserReporterAndUserReported(userReporter: User, userReported: User): Boolean

}
