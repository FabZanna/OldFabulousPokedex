package com.fabulouszanna.pokedex.ui.pokemondetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.fabulouszanna.pokedex.databinding.FragmentPokemonDetailsBinding
import com.fabulouszanna.pokedex.model.PokemonModel
import com.fabulouszanna.pokedex.repo.SinglePokemonViewModel
import com.fabulouszanna.pokedex.utilities.extractColorResourceFromType
import com.fabulouszanna.pokedex.utilities.setPokemonSprite
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_pokemon_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PokemonDetails : Fragment() {
    private lateinit var binding: FragmentPokemonDetailsBinding
    private val args: PokemonDetailsArgs by navArgs()
    private val pokemonViewModel: SinglePokemonViewModel by viewModel { parametersOf(args.pokemonId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPokemonDetailsBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pokemonViewModel.pokemon.observe(viewLifecycleOwner) { state ->
            state.pokemon.let { pokemon ->
                populateUpperView(pokemon)
                setupViewPager(pokemon)
            }
        }

        spinPokeball()
    }

    private fun setupViewPager(pokemon: PokemonModel) {
        val viewPagerAdapter = PokemonDetailsViewPagerAdapter(this, pokemon)
        viewPager.apply {
            adapter = viewPagerAdapter
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "About"
                1 -> "Base Stats"
                else -> position.toString()
            }
        }.attach()
    }

    private fun populateUpperView(pokemon: PokemonModel) {
        val pokemonColor = extractColorResourceFromType(requireContext(), pokemon.type1)
        binding.apply {
            pokemonName.text = pokemon.name
            pokemonType1.text = pokemon.type1
            pokemon.type2?.let {
                pokemonType2.text = it
                pokemonType2.visibility = View.VISIBLE
            }
            pokemonId.text = pokemon.id
            pokemonSpecies.text = pokemon.species
            setPokemonSprite(requireContext(), pokemon.imgUrl, pokemonImg)

            root.setBackgroundColor(pokemonColor)
        }
    }

    private fun spinPokeball() {
        val rotate = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = 5000
        rotate.repeatCount = Animation.INFINITE
        rotate.interpolator = LinearInterpolator()
        pokeball.animation = rotate
    }
}