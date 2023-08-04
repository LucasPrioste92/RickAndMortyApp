package com.lucasprioste.rickandmorty.core

fun containsSpecialCharacters(text: String): Boolean {
    val pattern = Regex("[^a-zA-Z0-9]")
    return pattern.containsMatchIn(text)
}