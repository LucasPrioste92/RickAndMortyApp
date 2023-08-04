package com.lucasprioste.rickandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucasprioste.rickandmorty.data.local.dao.RickAndMortyDao
import com.lucasprioste.rickandmorty.data.local.dao.RickAndMortyRemoteKeysDao
import com.lucasprioste.rickandmorty.data.local.entity.CharacterEntity
import com.lucasprioste.rickandmorty.data.local.entity.RickAndMortyRemoteKeys

@Database(
    entities = [CharacterEntity::class, RickAndMortyRemoteKeys::class],
    version = 1
)
abstract class RickAndMortyDatabase: RoomDatabase() {
    abstract val rickAndMortyDao: RickAndMortyDao
    abstract val remoteKeysDao: RickAndMortyRemoteKeysDao
}