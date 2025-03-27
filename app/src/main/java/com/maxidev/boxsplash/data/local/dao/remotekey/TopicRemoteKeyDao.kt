package com.maxidev.boxsplash.data.local.dao.remotekey

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.maxidev.boxsplash.data.local.entity.remotekey.TopicRemoteKeyEntity

@Dao
interface TopicRemoteKeyDao {

    @Query("SELECT * FROM topic_remote_key_table WHERE id = :id")
    suspend fun getRemoteKeys(id: String): TopicRemoteKeyEntity?

    @Query("SELECT lastUpdate FROM topic_remote_key_table WHERE lastUpdate = :time")
    suspend fun getLastUpdate(time: Long): Long

    @Upsert
    suspend fun upsertKeys(remoteKeys: List<TopicRemoteKeyEntity>)

    @Query("DELETE FROM topic_remote_key_table")
    suspend fun deleteRemoteKeys()
}