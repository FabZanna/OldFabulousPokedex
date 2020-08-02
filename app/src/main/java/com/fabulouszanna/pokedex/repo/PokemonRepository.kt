package com.fabulouszanna.pokedex.repo

import com.fabulouszanna.pokedex.model.PokemonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepository(
    private val pokemonDAO: PokemonEntity.PokemonDAO
) {
    fun pokemons(): Flow<List<PokemonModel>> =
        pokemonDAO.all().map { listOfEntities ->
            listOfEntities.map { pokemonEntity ->
                pokemonEntity.toModel()
            }
        }
}