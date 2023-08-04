package com.lucasprioste.rickandmorty.domain.model

data class Character(
    val id: Int = 0,
    val gender: Gender = Gender.Unknown,
    val image: String = "",
    val location: String = "",
    val name: String = "",
    val origin: String = "",
    val species: String = "",
    val status: Status = Status.Unknown,
)
