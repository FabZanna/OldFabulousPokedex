package com.fabulouszanna.pokedex.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fabulouszanna.pokedex.databinding.PokemonFilterBinding
import com.fabulouszanna.pokedex.utilities.enforceSingleScrollDirection
import com.fabulouszanna.pokedex.utilities.recyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.pokemon_filter.*

class FilterDialog(
    private val onGenFilterClicked: (String) -> Unit,
    private val onTypeFilterClicked: (String) -> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding: PokemonFilterBinding
    private lateinit var filterAdapter: FilterViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PokemonFilterBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filterAdapter = FilterViewPagerAdapter(this, onGenFilterClicked, onTypeFilterClicked)
        viewPager.apply {
            adapter = filterAdapter
            this.recyclerView.enforceSingleScrollDirection()
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Generation"
                1 -> tab.text = "Type(s)"
                else -> tab.text = "OBJECT ${(position + 1)}"
            }
        }.attach()

        // setStickyButton()
    }

    private fun setStickyButton() {
        val modelSheetBehavior = (this.dialog as BottomSheetDialog).behavior
        modelSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                val bottomSheetVisibleHeight = bottomSheet.height - bottomSheet.top
//                Log.d("POKEMON", bottomSheetVisibleHeight.toString())
//                submitButton.translationY =
//                    (bottomSheetVisibleHeight - submitButton.height).toFloat()

//                submitButton.animate().y(
//                    if (slideOffset <= 0)
//                        bottomSheet.y + modelSheetBehavior.peekHeight - submitButton.height
//                    else
//                        (bottomSheet.height - submitButton.height).toFloat()
//                ).setDuration(0).start()
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> binding.root.layoutParams.height =
                        modelSheetBehavior.peekHeight
                }
            }
        })
    }
}