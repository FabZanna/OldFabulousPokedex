package com.fabulouszanna.pokedex.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fabulouszanna.pokedex.model.PokemonModel
import kotlinx.coroutines.flow.map

data class SinglePokemonViewState(
    val pokemon: PokemonModel
)

class SinglePokemonViewModel(
    repository: PokemonRepository,
    pokemonId: String
) : ViewModel() {
    val pokemon: LiveData<SinglePokemonViewState> = repository.findPokemon(pokemonId).map { SinglePokemonViewState(it) }.asLiveData()
}