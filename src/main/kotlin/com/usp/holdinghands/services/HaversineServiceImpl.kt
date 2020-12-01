package com.usp.holdinghands.services

import org.springframework.stereotype.Service
import java.lang.Math.toRadians
import kotlin.math.*

@Service
class HaversineServiceImpl: HaversineService {

    override fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val λ1 = toRadians(lat1)
        val λ2 = toRadians(lat2)
        val Δλ = toRadians(lat2 - lat1)
        val Δφ = toRadians(lon2 - lon1)
        return 2 * Companion.R * asin(sqrt(sin(Δλ / 2).pow(2.0) + sin(Δφ / 2).pow(2.0) * cos(λ1) * cos(λ2)))
    }

    companion object {
        const val R = 6372.8 // in kilometers
    }
}
