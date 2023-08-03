package com.lucasprioste.rickandmorty.domain.model

import com.lucasprioste.rickandmorty.R

sealed class Status(val resourceId: Int, val name: String){
    object Alive: Status(resourceId = R.string.alive, name = "alive")
    object Dead: Status(resourceId = R.string.dead, name = "dead")
    object Unknown: Status(resourceId = R.string.unknown, name = "unknown")
}
