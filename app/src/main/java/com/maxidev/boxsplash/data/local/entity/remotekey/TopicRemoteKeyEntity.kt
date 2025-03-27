package com.maxidev.boxsplash.data.local.entity.remotekey

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topic_remote_key_table")
data class TopicRemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nextKey: Int?,
    val lastUpdate: Long
)