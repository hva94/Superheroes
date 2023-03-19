package com.hvasoft.superheroes.data.network

import com.hvasoft.superheroes.data.model.Superhero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SuperheroesService @Inject constructor(
    private val apiKeyProvider: ApiKeyProvider,
    private val apiClient: SuperheroesApiClient
){
    suspend fun getSuperheroById(id: String) : Superhero? {
        val apiKey = apiKeyProvider.getApiKey()

        return withContext(Dispatchers.IO) {
            apiClient.getSuperheroById(
                apiKey,
                id
            )
        }
    }
}