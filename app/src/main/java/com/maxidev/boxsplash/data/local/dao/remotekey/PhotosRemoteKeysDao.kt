package com.maxidev.boxsplash.data.local.dao.remotekey

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.maxidev.boxsplash.data.local.entity.remotekey.PhotosRemoteKeysEntity

@Dao
interface PhotosRemoteKeysDao {

    @Query("SELECT * FROM photos_remote_key_table WHERE id = :id")
    suspend fun getRemoteKeys(id: String): PhotosRemoteKeysEntity?

    @Query("SELECT lastUpdate FROM photos_remote_key_table WHERE lastUpdate = :time")
    suspend fun getLastUpdate(time: Long): Long

    @Upsert
    suspend fun upsertKeys(remoteKeys: List<PhotosRemoteKeysEntity>)

    @Query("DELETE FROM photos_remote_key_table")
    suspend fun deleteRemoteKeys()
}