package com.lucasprioste.rickandmorty.data.mapper

import com.lucasprioste.rickandmorty.domain.model.Gender
import com.lucasprioste.rickandmorty.domain.model.Status
import com.lucasprioste.rickandmorty.data.local.entity.CharacterEntity
import com.lucasprioste.rickandmorty.data.remote.dto.CharacterDto
import com.lucasprioste.rickandmorty.domain.model.Character

fun CharacterDto.toCharacterEntity(): CharacterEntity{
    return CharacterEntity(
        id = id,
        name = name,
        gender = gender,
        image = image,
        location = location.name,
        origin = origin.name,
        species = species,
        status = status
    )
}

fun CharacterEntity.toCharacter(): Character{
    return Character(
        id = id,
        name = name,
        species = species,
        status = getStatus(status),
        origin = origin,
        location = location,
        image = image,
        gender = getGender(gender)
    )
}

fun getGender(data: String): Gender {
    return when(data){
        "Male" -> Gender.Male
        "Female" -> Gender.Female
        "Genderless" -> Gender.Genderless
        else -> Gender.Unknown
    }
}

fun getStatus(data: String): Status {
    return when(data){
        "Alive" -> Status.Alive
        "Dead" -> Status.Dead
        else -> Status.Unknown
    }
}