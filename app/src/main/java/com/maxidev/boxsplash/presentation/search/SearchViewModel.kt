package com.maxidev.boxsplash.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.maxidev.boxsplash.data.local.entity.SearchHistoryEntity
import com.maxidev.boxsplash.domain.mappers.asDomain
import com.maxidev.boxsplash.domain.model.PhotoDomain
import com.maxidev.boxsplash.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {

    private val _state = MutableStateFlow<PagingData<PhotoDomain>>(PagingData.empty())
    val state = _state

    private val _expanded = mutableStateOf(false)
    val expanded = _expanded

    fun onExpandedChange(value: Boolean) {
        _expanded.value = value
    }

    private val _query = mutableStateOf("")
    val query = _query

    fun onQueryChange(value: String) {
        _query.value = value
    }

    fun clearQuery() {
        _query.value = ""
    }

    fun searchPhoto(query: String) {
        viewModelScope.launch {
            repository.searchPhotos(query)
                .cachedIn(viewModelScope)
                .catch { PagingData.empty<PhotoDomain>() }
                .collect { _state.value = it }
        }
    }

    // Data base operations

    /**
     * Displays recent search history up to a limit of 10.
     */
    val recentHistory: StateFlow<SearchUiState> =
        repository.getRecentSearches()
            .map { SearchUiState(recentHistory = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = SearchUiState()
            )

    /**
     * Filter search history based on matches. Limited to 10 results.
     */
    val searchHistory: StateFlow<SearchUiState> =
        repository.getSearchHistory(_query.value)
            .map { SearchUiState(searchHistory = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = SearchUiState()
            )

    fun saveInput(input: String) {
        val entity = SearchHistoryEntity(
            id = 0,
            input = input,
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            repository.saveInput(listOf(entity.asDomain()))
        }
    }

    fun deleteInput(input: String) =
        viewModelScope.launch {
            repository.deleteInput(input)
        }
}