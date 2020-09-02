package com.fabulouszanna.pokedex.ui.pokemondetails

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fabulouszanna.pokedex.model.PokemonModel
import com.fabulouszanna.pokedex.ui.pokemondetails.about.AboutFragment
import com.fabulouszanna.pokedex.ui.pokemondetails.basestats.BaseStatsFragment

class PokemonDetailsViewPagerAdapter(fragment: Fragment, private val pokemon: PokemonModel) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AboutFragment(pokemon)
            1 -> return BaseStatsFragment(pokemon)
        }
        return Fragment()
    }
}