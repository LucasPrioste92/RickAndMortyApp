package com.lucasprioste.rickandmorty.presentation.home_screen

import com.lucasprioste.rickandmorty.domain.model.FilterInfo

sealed class HomeContract{
    sealed class HomeEvent{
        data class OnSearch(val query: String): HomeEvent()
        data class OnChangeFilterStatus(val item: FilterInfo): HomeEvent()
        data class OnChangeFilterGender(val item: FilterInfo): HomeEvent()
    }
    sealed class HomeAction{

    }
}
