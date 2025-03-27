package com.maxidev.boxsplash.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val name: String? = "",
    val city: String? = "",
    val country: String? = "",
    val position: Position? = Position()
) {
    @Serializable
    data class Position(
        val latitude: Double? = 0.0,
        val longitude: Double? = 0.0
    )
}
