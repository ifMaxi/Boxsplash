package com.maxidev.boxsplash.domain.model

data class ExifDomain(
    val make: String,
    val model: String,
    val name: String,
    val exposureTime: String,
    val aperture: String,
    val focalLength: String,
    val iso: Int
)