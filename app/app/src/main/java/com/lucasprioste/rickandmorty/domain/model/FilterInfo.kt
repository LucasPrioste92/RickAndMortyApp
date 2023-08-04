package com.lucasprioste.rickandmorty.domain.model

data class FilterInfo(
    val resourceId: Int,
    val requestName: String,
    val isSelected: Boolean
)
