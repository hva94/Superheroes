package com.hvasoft.superheroes.data

import com.hvasoft.superheroes.data.model.Superhero
import com.hvasoft.superheroes.data.network.SuperheroesService
import com.hvasoft.superheroes.domain.repository.SuperheroesRepository
import javax.inject.Inject

class SuperheroesRepositoryImpl @Inject constructor(
    private val service: SuperheroesService
) : SuperheroesRepository {

    override suspend fun getSuperheroById(id: String): Superhero? {
        return service.getSuperheroById(id)
    }

}