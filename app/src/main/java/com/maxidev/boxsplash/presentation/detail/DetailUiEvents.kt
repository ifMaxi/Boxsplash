package com.maxidev.boxsplash.presentation.detail

import com.maxidev.boxsplash.domain.model.PhotoIdDomain

sealed interface DetailUiEvents {
    data object NavigateBack : DetailUiEvents
    data object OnShare : DetailUiEvents
    data object OpenInBrowser : DetailUiEvents
    data object OnDownload : DetailUiEvents
    data class SaveToBookMarks(val save: PhotoIdDomain) : DetailUiEvents
    data class DeleteToBookMarks(val delete: PhotoIdDomain) : DetailUiEvents
}