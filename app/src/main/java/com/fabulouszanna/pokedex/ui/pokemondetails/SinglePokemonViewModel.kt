package com.fabulouszanna.pokedex.ui.pokemondetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fabulouszanna.pokedex.model.PokemonModel
import com.fabulouszanna.pokedex.repo.pokemon.PokemonRepository
import kotlinx.coroutines.flow.map

data class SinglePokemonViewState(
    val pokemon: PokemonModel
)

class SinglePokemonViewModel(
    repository: PokemonRepository,
    id: Int
) : ViewModel() {
    val pokemon: LiveData<SinglePokemonViewState> =
        repository.findPokemon(id).map { SinglePokemonViewState(it) }.asLiveData()
}