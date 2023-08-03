package com.lucasprioste.rickandmorty.data.remote

import com.lucasprioste.rickandmorty.data.remote.dto.CharactersResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("gender") gender: String? = null,
    ): CharactersResponseDto

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}