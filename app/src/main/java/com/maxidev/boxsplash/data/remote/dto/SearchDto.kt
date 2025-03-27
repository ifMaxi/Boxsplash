package com.maxidev.boxsplash.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchDto(
    val results: List<PhotosDto>? = listOf()
)