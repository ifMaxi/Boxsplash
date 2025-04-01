package com.maxidev.boxsplash.di

import com.maxidev.boxsplash.data.repository.PhotoDetailRepositoryImpl
import com.maxidev.boxsplash.data.repository.PhotosRepositoryImpl
import com.maxidev.boxsplash.data.repository.SearchRepositoryImpl
import com.maxidev.boxsplash.domain.repository.PhotoDetailRepository
import com.maxidev.boxsplash.domain.repository.PhotosRepository
import com.maxidev.boxsplash.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPhotosRepository(impl: PhotosRepositoryImpl): PhotosRepository

    @Binds
    abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @Binds
    abstract fun bindPhotoDetailRepository(impl: PhotoDetailRepositoryImpl): PhotoDetailRepository
}