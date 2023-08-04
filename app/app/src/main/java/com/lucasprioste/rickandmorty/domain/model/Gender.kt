package com.lucasprioste.rickandmorty.domain.model

import com.lucasprioste.rickandmorty.R

sealed class Gender(val resourceId: Int, val name: String){
    object Male: Gender(resourceId = R.string.male, name = "male")
    object Female: Gender(resourceId = R.string.female, name = "female")
    object Genderless: Gender(resourceId = R.string.genderless, name = "genderless")
    object Unknown: Gender(resourceId = R.string.unknown, name = "unknown")
}