package com.fabulouszanna.pokedex.repo

import com.fabulouszanna.pokedex.model.PokemonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class PokemonRepository(
    private val pokemonDAO: PokemonEntity.PokemonDAO
) {
    fun pokemons(name: String = "", gen: String = "all", type: String = "all"): Flow<List<PokemonModel>> =
        filter(name, gen, type).map { listOfEntities ->
            listOfEntities.map { pokemonEntity ->
                pokemonEntity.toModel()
            }
        }

    private fun filter(name: String, gen: String, type: String): Flow<List<PokemonEntity>> {
        return if (name != "")
            pokemonDAO.filterByName(name)
        else if (gen == "all" && type == "all")
            pokemonDAO.all()
        else if (gen == "all") {
            pokemonDAO.filterByType(type)
        } else if (type == "all") {
            pokemonDAO.filterByGen(gen)
        } else {
            pokemonDAO.filtered(gen, type)
        }
    }

    fun findPokemon(pokemonId: String): Flow<PokemonModel> = pokemonDAO.findPokemon(pokemonId).map { it.toModel() }
}