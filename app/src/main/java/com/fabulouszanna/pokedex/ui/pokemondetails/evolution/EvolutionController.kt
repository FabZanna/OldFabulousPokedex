package com.fabulouszanna.pokedex.ui.pokemondetails.evolution

import androidx.constraintlayout.widget.ConstraintLayout
import com.fabulouszanna.pokedex.databinding.EvolutionArrowBinding
import com.fabulouszanna.pokedex.databinding.EvolutionItemBinding
import com.fabulouszanna.pokedex.databinding.FragmentEvolutionBinding
import com.fabulouszanna.pokedex.model.PokemonModel

class EvolutionController(
    private val binding: FragmentEvolutionBinding,
    private val pokemon: PokemonModel
) {
    init {
        createConstraintSet()
    }

    fun getPokemonMapping(): Map<Int, EvolutionItemBinding> {
        if (pokemon.splitEvolution) {
            if (pokemon.evolutions.size == 3) {
                return mapOf(
                    0 to binding.firstPokemon,
                    1 to binding.pokemonUp,
                    2 to binding.pokemonDown
                )
            }
            return mapOf(
                0 to binding.firstPokemon,
                1 to binding.secondPokemon,
                2 to binding.pokemonUp,
                3 to binding.pokemonDown
            )
        }
        return mapOf(
            0 to binding.firstPokemon,
            1 to binding.secondPokemon,
            2 to binding.thirdPokemon
        )
    }

    fun getArrowMapping(): Map<Int, EvolutionArrowBinding?> {
        if (pokemon.splitEvolution) {
            if (pokemon.evolutions.size == 3) {
                return mapOf(
                    0 to null,
                    1 to binding.evolutionCauseUpSplit,
                    2 to binding.evolutionCauseDownSplit
                )
            }
            return mapOf(
                0 to null,
                1 to binding.firstEvolutionCause,
                2 to binding.evolutionCauseUpSplit,
                3 to binding.evolutionCauseDownSplit
            )
        }
        return mapOf(0 to null, 1 to binding.firstEvolutionCause, 2 to binding.secondEvolutionCause)
    }

    private fun createConstraintSet() {
        if (pokemon.splitEvolution) {
            val viewsToModify = mutableListOf(
                binding.evolutionCauseUpSplit.root,
                binding.evolutionCauseDownSplit.root,
            )

            when (pokemon.evolutions.size) {
                3 -> viewsToModify.add(binding.firstPokemon.root)
                4 -> viewsToModify.add(binding.secondPokemon.root)
            }

            viewsToModify.forEach {
                val params = it.layoutParams as ConstraintLayout.LayoutParams
                if (it == binding.evolutionCauseUpSplit.root || it == binding.evolutionCauseDownSplit.root) {
                    params.startToEnd = viewsToModify.last().id
                } else {
                    params.endToStart = binding.evolutionCauseUpSplit.root.id
                }
                it.requestLayout()

            }
        }
    }
}