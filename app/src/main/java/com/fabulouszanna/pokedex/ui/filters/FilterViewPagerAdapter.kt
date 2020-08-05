package com.fabulouszanna.pokedex.ui.filters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterViewPagerAdapter(
    fragment: Fragment,
    private val dialog: BottomSheetDialogFragment,
    private val onFilterClicked: (String) -> Unit
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return GenerationFilter(dialog, onFilterClicked)
        }
        return Fragment()
    }
}