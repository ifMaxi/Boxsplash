package com.maxidev.boxsplash.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.maxidev.boxsplash.data.local.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM search_history_table ORDER BY timestamp DESC LIMIT 10")
    fun getRecentSearches(): Flow<List<SearchHistoryEntity>>

    @Query(
        "SELECT * FROM search_history_table WHERE input " +
                "LIKE '%' || :query || '%' " +
                "ORDER BY timestamp DESC LIMIT 10"
    )
    fun getSearchHistory(query: String): Flow<List<SearchHistoryEntity>>

    @Upsert
    suspend fun upsertInputs(entity: List<SearchHistoryEntity>)

    @Query("DELETE FROM search_history_table WHERE input = :input")
    suspend fun deleteInput(input: String)
}