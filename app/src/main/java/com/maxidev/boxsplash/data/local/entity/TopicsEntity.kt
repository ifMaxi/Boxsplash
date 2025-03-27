package com.maxidev.boxsplash.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topics_table")
data class TopicsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    @Embedded val coverPhoto: CoverPhotoEntity
)