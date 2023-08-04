package com.lucasprioste.rickandmorty.domain.model

data class FilterState(
    val searchName: String,
    val filterStatus: List<FilterInfo>,
    val filterGender: List<FilterInfo>
)
