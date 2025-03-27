package com.maxidev.boxsplash.domain.model

data class TopicsDomain(
    val id: String,
    val title: String,
    val coverPhotos: CoverPhotosDomain
)