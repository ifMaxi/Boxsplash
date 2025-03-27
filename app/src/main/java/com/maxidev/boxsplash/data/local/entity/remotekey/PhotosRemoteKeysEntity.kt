package com.maxidev.boxsplash.data.local.entity.remotekey

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos_remote_key_table")
data class PhotosRemoteKeysEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nextKey: Int?,
    val lastUpdate: Long
)