package com.maxidev.boxsplash.data.local.entity

import androidx.room.Embedded

data class CoverPhotoEntity(
    val blurHash: String,
    @Embedded val urls: UrlsEntity
)