package com.maxidev.boxsplash.domain.model

data class PhotoDomain(
    val id: String,
    val color: String,
    val blurHash: String,
    val urls: UrlsDomain
)