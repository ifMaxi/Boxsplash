package com.maxidev.boxsplash.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.maxidev.boxsplash.domain.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PhotosRepository
): ViewModel() {

    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState = _homeState.asStateFlow()

    init {
        listPhotos()
        listCollections()
        listTopics()
    }

    private fun listPhotos() {
        _homeState.update { photos ->
            photos.copy(
                photos = repository.fetchPhotos()
                    .cachedIn(viewModelScope)
            )
        }
    }

    private fun listCollections() {
        _homeState.update { collections ->
            collections.copy(
                collections = repository.fetchCollections()
                    .cachedIn(viewModelScope)
            )
        }
    }

    private fun listTopics() {
        _homeState.update { topics ->
            topics.copy(
                topics = repository.fetchTopics()
                    .cachedIn(viewModelScope)
            )
        }
    }
}