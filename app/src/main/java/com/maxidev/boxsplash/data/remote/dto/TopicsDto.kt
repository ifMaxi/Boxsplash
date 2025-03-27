package com.maxidev.boxsplash.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopicsDto(
    val id: String? = "",
    val title: String? = "",
    @SerialName("cover_photo")
    val coverPhoto: CoverPhotoDto? = CoverPhotoDto()
)