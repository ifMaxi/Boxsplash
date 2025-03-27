package com.maxidev.boxsplash.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.maxidev.boxsplash.data.local.entity.PhotosEntity

@Dao
interface PhotosDao {

    @Query("SELECT * FROM photos_table")
    fun pagingSource(): PagingSource<Int, PhotosEntity>

    @Upsert
    suspend fun upsertAll(entity: List<PhotosEntity>)

    @Query("DELETE FROM photos_table")
    suspend fun clearAll()
}