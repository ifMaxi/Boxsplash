package com.maxidev.boxsplash.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maxidev.boxsplash.data.local.dao.CollectionsDao
import com.maxidev.boxsplash.data.local.dao.PhotosDao
import com.maxidev.boxsplash.data.local.dao.SearchHistoryDao
import com.maxidev.boxsplash.data.local.dao.TopicsDao
import com.maxidev.boxsplash.data.local.dao.remotekey.CollectionsRemoteKeyDao
import com.maxidev.boxsplash.data.local.dao.remotekey.PhotosRemoteKeysDao
import com.maxidev.boxsplash.data.local.dao.remotekey.TopicRemoteKeyDao
import com.maxidev.boxsplash.data.local.entity.CollectionsEntity
import com.maxidev.boxsplash.data.local.entity.PhotosEntity
import com.maxidev.boxsplash.data.local.entity.SearchHistoryEntity
import com.maxidev.boxsplash.data.local.entity.TopicsEntity
import com.maxidev.boxsplash.data.local.entity.remotekey.CollectionsRemoteKeyEntity
import com.maxidev.boxsplash.data.local.entity.remotekey.PhotosRemoteKeysEntity
import com.maxidev.boxsplash.data.local.entity.remotekey.TopicRemoteKeyEntity

@Database(
    entities = [
        PhotosEntity::class,
        PhotosRemoteKeysEntity::class,
        CollectionsEntity::class,
        CollectionsRemoteKeyEntity::class,
        TopicsEntity::class,
        TopicRemoteKeyEntity::class,
        SearchHistoryEntity::class
               ],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun photosDao(): PhotosDao
    abstract fun photosRemoteKeyDao(): PhotosRemoteKeysDao

    abstract fun collectionsDao(): CollectionsDao
    abstract fun collectionsRemoteKeyDao(): CollectionsRemoteKeyDao

    abstract fun topicsDao(): TopicsDao
    abstract fun topicsRemoteKeyDao(): TopicRemoteKeyDao

    abstract fun searchHistoryDao(): SearchHistoryDao
}