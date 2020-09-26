package com.fabulouszanna.pokedex.ui.pokemondetails.evolution

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fabulouszanna.pokedex.databinding.EvolutionArrowBinding
import com.fabulouszanna.pokedex.databinding.FragmentEvolutionBinding
import com.fabulouszanna.pokedex.model.PokemonModel
import com.fabulouszanna.pokedex.ui.pokemondetails.PokemonDetailsDirections
import com.fabulouszanna.pokedex.ui.pokemonlist.PokemonViewModel
import com.fabulouszanna.pokedex.utilities.extractColorResourceFromType
import com.fabulouszanna.pokedex.utilities.setPokemonSprite
import org.koin.androidx.viewmodel.ext.android.viewModel

class EvolutionFragment(private val pokemon: PokemonModel) : Fragment() {
    private lateinit var binding: FragmentEvolutionBinding
    private val viewModel: PokemonViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentEvolutionBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val controller = EvolutionController(binding)

        viewModel.getPokemonsByName(pokemon.evolutions)
            .observe(viewLifecycleOwner) { evolutionList ->
                val sortedList = evolutionList.sortedBy { pokemon.evolutions.indexOf(it.name) }
                    .distinctBy { it.name }
                sortedList.filter { it.name == "Eevee" }.let {
                    if (it.isNotEmpty()) {
                        controller.inflateLayout(sortedList.size, isSplit = true, isEevee = true)
                    } else {
                        controller.inflateLayout(
                            sortedList.size,
                            isSplit = sortedList[0].splitEvolution
                        )
                    }
                }
                sortedList.forEach { pokemonModel ->
                    val (arrowLayout, pokemonLayout) = controller.getEvolutionComponents()
                    populateArrow(arrowLayout, pokemonModel.evolutionCause)
                    setPokemonSprite(
                        requireContext(),
                        pokemonModel.imgUrl,
                        pokemonLayout.pokemonImg
                    )
                    pokemonLayout.apply {
                        applyTypeStyling(pokemonType1, pokemonModel.type1)
                        applyTypeStyling(pokemonType2, pokemonModel.type2)

                        pokeball.strokeColor()
                        root.setOnClickListener { changePokemon(pokemonModel.id) }
                    }
                }
                controller.adjustArrowRotation()
            }
    }

    private fun changePokemon(id: Int) {
        if (pokemon.id != id) {
            findNavController().navigate(PokemonDetailsDirections.replacePokemonDetails(id))
        }
    }

    private fun ImageView.strokeColor() {
        val color = extractColorResourceFromType(requireContext(), pokemon.type1)
        this.imageTintList = ColorStateList.valueOf(color)
    }

    private fun applyTypeStyling(tv: TextView, type: String?) {
        type?.let {
            val color = extractColorResourceFromType(requireContext(), it)
            tv.text = it
            tv.setTextColor(color)
        }
    }

    private fun populateArrow(layout: EvolutionArrowBinding?, evolutionCause: String?) {
        if (layout == null) {
            return
        }
        evolutionCause?.let {
            layout.evolutionCause.text = it
        }
    }
}