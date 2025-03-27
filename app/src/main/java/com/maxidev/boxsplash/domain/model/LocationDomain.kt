package com.maxidev.boxsplash.domain.model

data class LocationDomain(
    val name: String,
    val city: String,
    val country: String,
    val position: PositionDomain
) {
    data class PositionDomain(
        val latitude: Double,
        val longitude: Double
    )
}