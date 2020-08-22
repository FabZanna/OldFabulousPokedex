package com.fabulouszanna.pokedex.ui.filters

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.fabulouszanna.pokedex.R
import com.fabulouszanna.pokedex.databinding.PokemonFilterBinding
import com.fabulouszanna.pokedex.utilities.enforceSingleScrollDirection
import com.fabulouszanna.pokedex.utilities.recyclerView
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val coordinator =
                (it as BottomSheetDialog).findViewById<CoordinatorLayout>(com.google.android.material.R.id.coordinator)
            val containerLayout =
                it.findViewById<FrameLayout>(com.google.android.material.R.id.container)
            val filterButton = bottomSheetDialog.layoutInflater.inflate(R.layout.filter_button, null)

            filterButton.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM
            }
            containerLayout!!.addView(filterButton)

            /*
            * Dynamically update bottom sheet containerLayout bottom margin to filterButtons view height
            * */
            filterButton.post {
                (coordinator!!.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    filterButton.measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    )
                    this.bottomMargin = filterButton.measuredHeight
                    containerLayout.requestLayout()
                }
            }

        }

        return bottomSheetDialog
    }

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
    }
}