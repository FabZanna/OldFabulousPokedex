package com.fabulouszanna.pokedex.repo

import com.fabulouszanna.pokedex.model.PokemonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepository(
    private val pokemonDAO: PokemonEntity.PokemonDAO
) {
    fun pokemons(gen: String = "all"): Flow<List<PokemonModel>> =
        filterByGen(gen).map { listOfEntities ->
            listOfEntities.map { pokemonEntity ->
                pokemonEntity.toModel()
            }
        }

    private fun filterByGen(gen: String) = when (gen) {
        "all" -> pokemonDAO.all()
        else -> pokemonDAO.filtered(gen)
    }
}