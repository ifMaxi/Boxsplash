package com.maxidev.boxsplash.domain.model

data class CollectionDomain(
    val id: String,
    val title: String,
    val totalPhotos: Int,
    val coverPhotos: CoverPhotosDomain
)