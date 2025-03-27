package com.maxidev.boxsplash.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections_table")
data class CollectionsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val totalPhotos: Int,
    @Embedded val coverPhotos: CoverPhotoEntity
)