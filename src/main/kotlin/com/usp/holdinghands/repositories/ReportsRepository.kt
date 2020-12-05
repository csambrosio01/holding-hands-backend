package com.usp.holdinghands.repositories

import com.usp.holdinghands.models.Reports
import org.springframework.data.repository.CrudRepository

interface ReportsRepository: CrudRepository<Reports, Long> {
}
