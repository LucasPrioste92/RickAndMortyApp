package com.lucasprioste.rickandmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.lucasprioste.rickandmorty.data.local.entity.RickAndMortyRemoteKeys

@Dao
interface RickAndMortyRemoteKeysDao {
    @Upsert
    suspend fun upsertAllRemoteKeys(remoteKeys: List<RickAndMortyRemoteKeys>)

    @Query("SELECT * FROM rickandmortyremotekeys WHERE id == :id")
    suspend fun getRemoteKeys(id: Int): RickAndMortyRemoteKeys

    @Query("DELETE FROM rickandmortyremotekeys")
    suspend fun clearAll()
}