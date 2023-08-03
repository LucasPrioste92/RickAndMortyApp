package com.lucasprioste.rickandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucasprioste.rickandmorty.data.local.entity.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1
)
abstract class RickAndMortyDatabase: RoomDatabase() {

    abstract val dao: RickAndMortyDao
}