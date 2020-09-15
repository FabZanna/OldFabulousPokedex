package com.fabulouszanna.pokedex.repo.pokemonmove

import kotlinx.coroutines.flow.map

class PokemonMoveRepository(private val pokemonMoveDAO: PokemonMoveEntity.PokemonMoveDAO) {
    fun getPokemonMoves(pokemonName: String, learnedBy: String) =
        pokemonMoveDAO.getPokemonMoves(pokemonName, learnedBy).map { list ->
            list.map { it.toModel() }
        }
}