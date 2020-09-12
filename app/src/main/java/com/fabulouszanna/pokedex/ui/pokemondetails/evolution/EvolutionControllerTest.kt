package com.fabulouszanna.pokedex.ui.pokemondetails.evolution

import androidx.constraintlayout.widget.ConstraintLayout
import com.fabulouszanna.pokedex.databinding.EvolutionArrowBinding
import com.fabulouszanna.pokedex.databinding.EvolutionItemBinding
import com.fabulouszanna.pokedex.databinding.FragmentEvolutionBinding
import com.fabulouszanna.pokedex.model.PokemonModel

class EvolutionControllerTest(
    private val binding: FragmentEvolutionBinding,
    // private val pokemon: PokemonModel
) {
    private val root = binding.root

    fun addPokemon() {

    }

//    fun getPokemonMapping(): Map<Int, EvolutionItemBinding> {
//        if (pokemon.splitEvolution) {
//            if (pokemon.evolutions.size == 3) {
//                return mapOf(
//                    0 to binding.firstPokemon,
//                    1 to binding.pokemonUp,
//                    2 to binding.pokemonDown
//                )
//            }
//            return mapOf(
//                0 to binding.firstPokemon,
//                1 to binding.secondPokemon,
//                2 to binding.pokemonUp,
//                3 to binding.pokemonDown
//            )
//        }
//        return mapOf(
//            0 to binding.firstPokemon,
//            1 to binding.secondPokemon,
//            2 to binding.thirdPokemon
//        )
//    }
//
//    fun getArrowMapping(): Map<Int, EvolutionArrowBinding?> {
//        if (pokemon.splitEvolution) {
//            if (pokemon.evolutions.size == 3) {
//                return mapOf(
//                    0 to null,
//                    1 to binding.evolutionCauseUpSplit,
//                    2 to binding.evolutionCauseDownSplit
//                )
//            }
//            return mapOf(
//                0 to null,
//                1 to binding.firstEvolutionCause,
//                2 to binding.evolutionCauseUpSplit,
//                3 to binding.evolutionCauseDownSplit
//            )
//        }
//        return mapOf(0 to null, 1 to binding.firstEvolutionCause, 2 to binding.secondEvolutionCause)
//    }
//
//    private fun createConstraintSet() {
//        if (pokemon.splitEvolution && pokemon.evolutions.size == 3) {
//            val viewsToModify = listOf(
//                binding.evolutionCauseUpSplit.root,
//                binding.evolutionCauseDownSplit.root,
//                binding.firstPokemon.root
//            )
//            viewsToModify.forEach {
//                val params = it.layoutParams as ConstraintLayout.LayoutParams
//                if (it == binding.firstPokemon.root) {
//                    params.endToStart = binding.evolutionCauseUpSplit.root.id
//                } else {
//                    params.startToEnd = binding.firstPokemon.root.id
//                }
//                it.requestLayout()
//
//            }
//        }
//    }
}