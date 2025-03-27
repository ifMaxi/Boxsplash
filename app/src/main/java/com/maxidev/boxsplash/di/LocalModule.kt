package com.maxidev.boxsplash.di

import android.content.Context
import androidx.room.Room
import com.maxidev.boxsplash.data.local.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    private const val DATABASE_NAME = "boxsplash_db"

    @Provides
    @Singleton
    fun providesRoomDataBase(
        @ApplicationContext context: Context
    ): AppDataBase {

        return Room.databaseBuilder(
            context = context,
            klass = AppDataBase::class.java,
            name = DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}