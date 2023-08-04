package com.lucasprioste.rickandmorty.data.remote.dto

data class CharacterDto(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: DataDto,
    val name: String,
    val origin: DataDto,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)