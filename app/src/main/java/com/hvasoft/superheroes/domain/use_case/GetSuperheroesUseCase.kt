package com.hvasoft.superheroes.domain.use_case

import com.hvasoft.superheroes.core.Constants
import com.hvasoft.superheroes.data.model.Superhero
import com.hvasoft.superheroes.domain.repository.SuperheroesRepository
import javax.inject.Inject

class GetSuperheroesUseCase @Inject constructor(
    private val repository: SuperheroesRepository
) {

    suspend operator fun invoke(lastId: Int): List<Superhero> {
        val list = mutableListOf<Superhero>()
        for (id in lastId..lastId + Constants.ID_INCREMENT) {
            val superhero = repository.getSuperheroById(id.toString())
            superhero?.let {
                list.add(it)
            }
        }
        return list
    }

}