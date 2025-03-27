package com.maxidev.boxsplash.domain.model

data class SearchHistoryDomain(
    val id: Long,
    val input: String,
    val timestamp: Long
)