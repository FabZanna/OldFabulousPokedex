package com.fabulouszanna.pokedex.ui.pokemondetails

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.fabulouszanna.pokedex.databinding.FragmentPokemonDetailsBinding
import com.fabulouszanna.pokedex.model.PokemonModel
import com.fabulouszanna.pokedex.utilities.extractColorResourceFromType
import com.fabulouszanna.pokedex.utilities.setPokemonSprite
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_pokemon_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PokemonDetails : Fragment() {
    private lateinit var binding: FragmentPokemonDetailsBinding
    private val args: PokemonDetailsArgs by navArgs()
    private val pokemonViewModel: SinglePokemonViewModel by viewModel { parametersOf(args.id) }
    private var pokemonColor: Int = 0

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPokemonDetailsBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pokemonViewModel.pokemon.observe(viewLifecycleOwner) { state ->
            state.pokemon.let { pokemon ->
                pokemonColor = extractColorResourceFromType(requireContext(), pokemon.type1)
                populateUpperView(pokemon)
                setupViewPager(pokemon)
            }
        }

        spinPokeBall()
    }

    private fun onPopStackBack() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (binding.viewPager.currentItem != 1) {
                binding.viewPager.currentItem = 1
            }
        }, 260)
    }

    private fun setupViewPager(pokemon: PokemonModel) {
        val viewPagerAdapter =
            PokemonDetailsViewPagerAdapter(this, pokemon, onPopBackStack = ::onPopStackBack)
        binding.apply {
            viewPager.apply {
                adapter = viewPagerAdapter
            }

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "About"
                    1 -> "Base Stats"
                    2 -> "Evolution"
                    3 -> "Moves"
                    else -> position.toString()
                }
            }.attach()

            tabLayout.setSelectedTabIndicatorColor(pokemonColor)
            tabLayout.setTabTextColors(Color.DKGRAY, pokemonColor)
        }
    }

    private fun populateUpperView(pokemon: PokemonModel) {
        binding.apply {
            pokemonName.text = pokemon.name
            pokemon.variationName?.let {
                val varName = "($it)"
                pokemonVariationName.text = varName
                pokemonVariationName.visibility = View.VISIBLE
                changeSpeciesConstraints()
            }
            pokemonType1.text = pokemon.type1
            pokemon.type2?.let {
                pokemonType2.text = it
                pokemonType2.visibility = View.VISIBLE
            }
            pokemonId.text = pokemon.pokemonId
            pokemonSpecies.text = pokemon.species
            setPokemonSprite(requireContext(), pokemon.imgUrl, pokemonImg)

            root.setBackgroundColor(pokemonColor)
        }
    }

    private fun changeSpeciesConstraints() {
        val params = pokemonSpecies.layoutParams as ConstraintLayout.LayoutParams
        params.baselineToBaseline = binding.pokemonVariationName.id
        binding.root.requestLayout()
    }

    private fun spinPokeBall() {
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