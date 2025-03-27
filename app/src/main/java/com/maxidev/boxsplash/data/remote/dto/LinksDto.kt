package com.maxidev.boxsplash.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LinksDto(
    val html: String? = "",
    val download: String? = ""
)