package com.maxidev.boxsplash.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history_table")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val input: String,
    val timestamp: Long
)