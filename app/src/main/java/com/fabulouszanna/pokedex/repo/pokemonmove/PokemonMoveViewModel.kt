package com.fabulouszanna.pokedex.repo.pokemonmove

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fabulouszanna.pokedex.model.PokemonAndMoveModel
import kotlinx.coroutines.flow.map

data class PokemonMoveViewState(
    val pokemonAndMove: List<PokemonAndMoveModel>,
    val learnMethod: String = "level_up"
)

class PokemonMoveViewModel(
    private val repository: PokemonMoveRepository,
    private val pokemonName: String
) : ViewModel() {
    private val _pokemonMove = MediatorLiveData<PokemonMoveViewState>()
    val pokemonMove: LiveData<PokemonMoveViewState> = _pokemonMove
    private val lastSource: LiveData<PokemonMoveViewState>? = null

    init {
        moveLearnedBy("level_up")
    }

    fun moveLearnedBy(learnMethod: String) {
        lastSource?.let { _pokemonMove.removeSource(it) }
        val items = repository.getPokemonMoves(pokemonName, learnMethod)
            .map { PokemonMoveViewState(it, learnMethod) }.asLiveData()
        _pokemonMove.addSource(items) {viewState ->
            _pokemonMove.value = viewState
        }
    }
}