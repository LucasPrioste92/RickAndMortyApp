package com.lucasprioste.rickandmorty.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.lucasprioste.rickandmorty.data.local.RickAndMortyDatabase
import com.lucasprioste.rickandmorty.data.local.entity.CharacterEntity
import com.lucasprioste.rickandmorty.data.remote.CharacterRemoteMediator
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

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideRickAndMortyPager(rickAndMortyDatabase: RickAndMortyDatabase, rickAndMortyApi: RickAndMortyApi): Pager<Int, CharacterEntity>{
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CharacterRemoteMediator(
                rickAndMortyApi = rickAndMortyApi,
                rickAndMortyDb = rickAndMortyDatabase,
            ),
            pagingSourceFactory = {
                rickAndMortyDatabase.dao.pagingSource()
            }
        )
    }

    @Provides
    @Singleton
    fun provideRickAndMortyRepository(pager: Pager<Int, CharacterEntity>, db: RickAndMortyDatabase): RickAndMortyRepository{
        return RickAndMortyRepositoryImp(pager = pager, db = db)
    }

    @Provides
    @Singleton
    fun provideGetCharactersUseCase(repository: RickAndMortyRepository): GetCharacters{
        return GetCharacters(repository)
    }
}