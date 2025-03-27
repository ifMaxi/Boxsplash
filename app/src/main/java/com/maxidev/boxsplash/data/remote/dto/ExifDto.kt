package com.maxidev.boxsplash.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExifDto(
    val make: String? = "",
    val model: String? = "",
    val name: String? = "",
    @SerialName("exposure_time")
    val exposureTime: String? = "",
    val aperture: String? = "",
    @SerialName("focal_length")
    val focalLength: String? = "",
    val iso: Int? = 0
)