package com.maxidev.boxsplash.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotosDto(
    val id: String? = "",
    val color: String? = "",
    @SerialName("blur_hash")
    val blurHash: String? = "",
    val urls: UrlsDto? = UrlsDto()
)