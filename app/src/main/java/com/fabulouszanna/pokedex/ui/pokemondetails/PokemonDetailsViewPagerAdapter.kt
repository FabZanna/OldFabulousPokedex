package com.fabulouszanna.pokedex.ui.pokemondetails

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fabulouszanna.pokedex.model.PokemonModel
import com.fabulouszanna.pokedex.ui.pokemondetails.about.AboutFragment
import com.fabulouszanna.pokedex.ui.pokemondetails.basestats.BaseStatsFragment
import com.fabulouszanna.pokedex.ui.pokemondetails.evolution.EvolutionFragment
import com.fabulouszanna.pokedex.ui.pokemondetails.move.MoveFragment

class PokemonDetailsViewPagerAdapter(fragment: Fragment, private val pokemon: PokemonModel, private val onPopBackStack: () -> Unit) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AboutFragment(pokemon)
            1 -> return BaseStatsFragment(pokemon, onPopBackStack)
            2 -> return EvolutionFragment(pokemon)
            3 -> return MoveFragment(pokemon)
        }
        return Fragment()
    }
}