package com.maxidev.boxsplash.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoverPhotoDto(
    @SerialName("blur_hash")
    val blurHash: String? = "",
    val urls: UrlsDto? = UrlsDto()
)