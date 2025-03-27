package com.maxidev.boxsplash.presentation.search

import com.maxidev.boxsplash.domain.model.SearchHistoryDomain

data class SearchUiState(
    val recentHistory: List<SearchHistoryDomain> = emptyList(),
    val searchHistory: List<SearchHistoryDomain> = emptyList()
)