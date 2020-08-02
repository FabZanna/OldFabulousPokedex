package com.fabulouszanna.pokedex.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fabulouszanna.pokedex.model.PokemonModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class PokemonViewState(
    val pokemons: List<PokemonModel> = listOf()
)

class PokemonViewModel(private val repo: PokemonRepository) : ViewModel() {
    val pokemons = repo.pokemons().map { PokemonViewState(it) }.asLiveData()
}