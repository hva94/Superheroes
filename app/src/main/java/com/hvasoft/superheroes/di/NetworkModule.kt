package com.hvasoft.superheroes.di

import android.app.Application
import com.hvasoft.superheroes.core.Constants
import com.hvasoft.superheroes.data.SuperheroesRepositoryImpl
import com.hvasoft.superheroes.data.network.ApiKeyProvider
import com.hvasoft.superheroes.data.network.SuperheroesApiClient
import com.hvasoft.superheroes.data.network.SuperheroesService
import com.hvasoft.superheroes.domain.repository.SuperheroesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideSuperheroesApiClient(retrofit: Retrofit): SuperheroesApiClient {
        return retrofit.create(SuperheroesApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideApiKeyProvider(application: Application): ApiKeyProvider {
        return ApiKeyProvider(application)
    }

    @Singleton
    @Provides
    fun provideSuperheroesRepository(
        service: SuperheroesService
    ): SuperheroesRepository {
        return SuperheroesRepositoryImpl(service)
    }

}