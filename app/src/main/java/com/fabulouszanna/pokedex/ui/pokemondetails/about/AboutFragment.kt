package com.fabulouszanna.pokedex.ui.pokemondetails.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fabulouszanna.pokedex.databinding.FragmentAboutBinding
import com.fabulouszanna.pokedex.model.PokemonModel

class AboutFragment(private val pokemon: PokemonModel) : Fragment() {
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentAboutBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            val height = "${pokemon.height} m"
            val weight = "${pokemon.weight} kg"
            pokemonDescription.text = pokemon.description
            pokemonHeight.text = height
            pokemonWeight.text = weight
            malePerc.text = pokemon.malePercentage
            femalePerc.text = pokemon.femalePercentage

            if (pokemon.malePercentage == "0" && pokemon.femalePercentage == "0") {
                hasGenders.visibility = View.GONE
                genderless.visibility = View.VISIBLE
            }
        }
    }
}