package com.hvasoft.superheroes.domain.repository

import com.hvasoft.superheroes.data.model.Superhero

interface SuperheroesRepository {

    suspend fun getSuperheroById(id: String): Superhero?

}