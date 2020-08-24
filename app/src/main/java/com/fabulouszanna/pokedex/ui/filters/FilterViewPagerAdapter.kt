package com.fabulouszanna.pokedex.ui.filters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FilterViewPagerAdapter(
    fragment: Fragment,
    private val onGenFilterClicked: (String) -> Unit,
    private val onTypeFilterClicked: (String) -> Unit
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return FilterFragment(
                adapterType = "generation",
                onFilterClicked = onGenFilterClicked
            )
            1 -> return FilterFragment(
                adapterType = "type",
                onFilterClicked = onTypeFilterClicked
            )
        }
        return Fragment()
    }
}