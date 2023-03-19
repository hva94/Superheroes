package com.hvasoft.superheroes.data.network

import com.hvasoft.superheroes.core.Constants
import com.hvasoft.superheroes.data.model.Superhero
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperheroesApiClient {

    @GET(Constants.GET_PATH)
    suspend fun getSuperheroById(
        @Path(Constants.APIKEY_PARAM) apiKey: String,
        @Path(Constants.ID_PARAM) id: String
    ): Superhero?

}