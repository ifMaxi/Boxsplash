package com.maxidev.boxsplash.data.local.dao.remotekey

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.maxidev.boxsplash.data.local.entity.remotekey.CollectionsRemoteKeyEntity

@Dao
interface CollectionsRemoteKeyDao {

    @Query("SELECT * FROM collections_remote_key_table WHERE id = :id")
    suspend fun getRemoteKeys(id: String): CollectionsRemoteKeyEntity?

    @Query("SELECT lastUpdate FROM collections_remote_key_table WHERE lastUpdate = :time")
    suspend fun getLastUpdate(time: Long): Long

    @Upsert
    suspend fun upsertKeys(remoteKeys: List<CollectionsRemoteKeyEntity>)

    @Query("DELETE FROM collections_remote_key_table")
    suspend fun deleteRemoteKeys()
}