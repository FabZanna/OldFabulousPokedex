package com.fabulouszanna.pokedex.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fabulouszanna.pokedex.model.PokemonModel
import kotlinx.coroutines.flow.map

data class PokemonViewState(
    val pokemons: List<PokemonModel> = listOf(),
    val gen: String = "all"
)

class PokemonViewModel(private val repo: PokemonRepository) : ViewModel() {
    private val _pokemons = MediatorLiveData<PokemonViewState>()
    val pokemons: LiveData<PokemonViewState> = _pokemons
    private val lastSource: LiveData<PokemonViewState>? = null
    // val pokemons = repo.pokemons().map { PokemonViewState(it) }.asLiveData()

    init {
        filterByGen("all")
    }

    fun filterByGen(gen: String) {
        lastSource?.let { _pokemons.removeSource(it) }
        val items = repo.pokemons(gen).map { PokemonViewState(it, gen) }.asLiveData()
        _pokemons.addSource(items) { viewState ->
            _pokemons.value = viewState
        }
    }
}