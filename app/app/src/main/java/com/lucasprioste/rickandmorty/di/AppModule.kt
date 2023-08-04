package com.lucasprioste.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.lucasprioste.rickandmorty.data.local.RickAndMortyDatabase
import com.lucasprioste.rickandmorty.data.remote.RickAndMortyApi
import com.lucasprioste.rickandmorty.data.repository.RickAndMortyRepositoryImp
import com.lucasprioste.rickandmorty.domain.repository.RickAndMortyRepository
import com.lucasprioste.rickandmorty.domain.use_case.GetCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRickAndMortyDatabase(@ApplicationContext context: Context): RickAndMortyDatabase{
        return Room.databaseBuilder(
            context,
            RickAndMortyDatabase::class.java,
            "rickandmorty.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRickAndMortyApi(): RickAndMortyApi{
        return Retrofit.Builder()
            .baseUrl(RickAndMortyApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideRickAndMortyRepository(api: RickAndMortyApi, db: RickAndMortyDatabase): RickAndMortyRepository{
        return RickAndMortyRepositoryImp(api = api, db = db)
    }

    @Provides
    @Singleton
    fun provideGetCharactersUseCase(repository: RickAndMortyRepository): GetCharacters{
        return GetCharacters(repository)
    }
}