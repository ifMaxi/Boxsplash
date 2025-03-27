package com.maxidev.boxsplash.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos_table")
data class PhotosEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val color: String,
    val blurHash: String,
    @Embedded val urls: UrlsEntity
)