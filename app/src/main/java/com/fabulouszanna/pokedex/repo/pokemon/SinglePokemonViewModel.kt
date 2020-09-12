package com.fabulouszanna.pokedex.repo.pokemon

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
    id: Int
) : ViewModel() {
    val pokemon: LiveData<SinglePokemonViewState> =
        repository.findPokemon(id).map { SinglePokemonViewState(it) }.asLiveData()
}