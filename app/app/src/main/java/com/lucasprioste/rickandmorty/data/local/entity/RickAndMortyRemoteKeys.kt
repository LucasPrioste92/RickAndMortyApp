package com.lucasprioste.rickandmorty.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RickAndMortyRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
)
