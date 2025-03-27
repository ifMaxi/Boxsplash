package com.maxidev.boxsplash.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.maxidev.boxsplash.data.local.entity.TopicsEntity

@Dao
interface TopicsDao {

    @Query("SELECT * FROM topics_table")
    fun topicSource(): PagingSource<Int, TopicsEntity>

    @Upsert
    suspend fun upsertAll(entity: List<TopicsEntity>)

    @Query("DELETE FROM topics_table")
    suspend fun clearAll()
}