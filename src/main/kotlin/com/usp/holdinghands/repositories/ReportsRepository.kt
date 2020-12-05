package com.usp.holdinghands.repositories

import com.usp.holdinghands.models.Reports
import com.usp.holdinghands.models.ReportsPrimaryKey
import org.springframework.data.repository.CrudRepository

interface ReportsRepository: CrudRepository<Reports, Long> {
    fun findAllByReported (userReported: Long): Int
}
