package com.fabulouszanna.pokedex.ui.filters

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fabulouszanna.pokedex.R
import com.fabulouszanna.pokedex.databinding.PokemonFilterBinding
import com.fabulouszanna.pokedex.utilities.enforceSingleScrollDirection
import com.fabulouszanna.pokedex.utilities.recyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.pokemon_filter.*

class FilterDialog(private val onFilterClicked: (String) -> Unit) : BottomSheetDialogFragment() {
    private lateinit var binding: PokemonFilterBinding
    private lateinit var filterAdapter: FilterViewPagerAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PokemonFilterBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filterAdapter = FilterViewPagerAdapter(this, this@FilterDialog, onFilterClicked)
        viewPager.apply {
            adapter = filterAdapter
            this.recyclerView.enforceSingleScrollDirection()
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Generation"
                else -> tab.text = "OBJECT ${(position + 1)}"
            }
        }.attach()
    }
}