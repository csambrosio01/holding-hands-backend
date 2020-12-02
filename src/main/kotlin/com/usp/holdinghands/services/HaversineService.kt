package com.usp.holdinghands.services

interface HaversineService {
    fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double
}
