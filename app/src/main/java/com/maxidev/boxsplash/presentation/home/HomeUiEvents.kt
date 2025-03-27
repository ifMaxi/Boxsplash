package com.maxidev.boxsplash.presentation.home

sealed interface HomeUiEvents {
    data object NavigateToSearch: HomeUiEvents
    data object NavigateToBookmarks: HomeUiEvents
    data object NavigateToSettings: HomeUiEvents
    data class NavigateToPhotoDetail(val id: String): HomeUiEvents
    data class NavigateToCollectionContent(val id: String): HomeUiEvents
    data class NavigateToTopicContent(val id: String): HomeUiEvents
}