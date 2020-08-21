package com.fabulouszanna.pokedex.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fabulouszanna.pokedex.model.PokemonModel
import kotlinx.coroutines.flow.map

data class PokemonViewState(
    val pokemons: List<PokemonModel> = listOf(),
    val name: String = "",
    val gen: String = "all",
    val type: String = "all"
)

class PokemonViewModel(private val repo: PokemonRepository) : ViewModel() {
    private val _pokemons = MediatorLiveData<PokemonViewState>()
    val pokemons: LiveData<PokemonViewState> = _pokemons
    private val lastSource: LiveData<PokemonViewState>? = null

    init {

        filtered(name = "", gen = "all", type = "all")
    }

    fun filtered(name: String, gen: String, type: String) {
        lastSource?.let { _pokemons.removeSource(it) }
        val items = repo.pokemons(name, gen, type).map { PokemonViewState(it, name, gen, type) }
            .asLiveData()
        _pokemons.addSource(items) { viewState ->
            _pokemons.value = viewState
        }
    }
}