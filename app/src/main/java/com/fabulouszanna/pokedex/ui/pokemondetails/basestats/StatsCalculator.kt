package com.fabulouszanna.pokedex.ui.pokemondetails.basestats

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.alpha
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fabulouszanna.pokedex.R
import com.fabulouszanna.pokedex.databinding.FragmentCalculateStatsBinding
import com.fabulouszanna.pokedex.utilities.toPx
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.calculate_stats_single_stat.view.*

class StatsCalculator : Fragment() {
    private lateinit var binding: FragmentCalculateStatsBinding
    private val helper = StatsCalculatorHelper()
    private var color = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCalculateStatsBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            color = it.getInt("pokemonColor")
        }
        bindButtons()
        bindNumberPickers()

        val stats = resources.getStringArray(R.array.stats)
        val container = binding.root.getChildAt(0) as LinearLayout
        stats.forEachIndexed { index, stat ->
            container.getChildAt(index).apply {
                changeViewColor()
                val currentStat = this
                statName.text = stat

                IV.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        IVText.text = "31 IV"
                        helper.storeIVs(index, 31)
                    } else {
                        IVText.text = "0 IV"
                        helper.storeIVs(index, 0)
                    }
                }

                EV.tag = index
                EV.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekbar: SeekBar,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        val adjustedProgress = helper.getProgressWithStep(progress)
                        setEVText(seekbar, adjustedProgress, currentStat.EVText)
                        seekbar.progress = adjustedProgress
                        helper.limitSeekBars(seekbar, adjustedProgress)
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                    }

                })
            }
        }
    }

    private fun bindButtons() {
        binding.apply {
            cancel.setOnClickListener { findNavController().popBackStack() }
            submit.apply {
                backgroundTintList = ColorStateList.valueOf(color)
                setOnClickListener { setNavigationResult() }
            }
        }
    }

    private fun bindNumberPickers() {
        binding.apply {
            levelPicker.apply {
                minValue = 1
                maxValue = 100
                setOnValueChangedListener { _, _, newValue -> helper.levelPicked = newValue }
                changeViewColor()
            }
            naturePicker.apply {
                val natures = resources.getStringArray(R.array.natures)
                minValue = 0
                maxValue = natures.size - 1
                displayedValues = natures
                setOnValueChangedListener { _, _, newValue ->
                    helper.naturePicked = natures[newValue]
                }
                changeViewColor()
            }
        }
    }

    private fun View.changeViewColor() {
        val viewToChange =
            if (this is ConstraintLayout) this.getChildAt(0) as View else this.parent as View
        (viewToChange.background as GradientDrawable).setStroke(1.toPx(), color)

        val viewName = (viewToChange.parent as ViewGroup).getChildAt(1) as TextView
        viewName.setTextColor(color)

        if (this is ConstraintLayout) {
            val seekbar: SeekBar = viewToChange.findViewById(R.id.EV)
            seekbar.progressTintList = ColorStateList.valueOf(color)
            seekbar.thumbTintList = ColorStateList.valueOf(color)

            val switch: SwitchMaterial = viewToChange.findViewById(R.id.IV)
            val states = arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            )

            val thumbColors = intArrayOf(color, Color.LTGRAY)
            // Alpha must be between 0 and 255
            val alpha = (0.6 * 255).toInt()
            val trackColors = intArrayOf(ColorUtils.setAlphaComponent(color, alpha), Color.GRAY)

            switch.thumbTintList = ColorStateList(states, thumbColors)
            switch.trackTintList = ColorStateList(states, trackColors)
        }
    }

    private fun setEVText(seekbar: SeekBar, progress: Int, evTextView: TextView) {
        val offset =
            (seekbar.width - seekbar.paddingLeft - seekbar.paddingRight) * progress / seekbar.max
        evTextView.x = seekbar.x + seekbar.paddingLeft - seekbar.paddingRight + offset

        val tvWidth = evTextView.x + evTextView.width
        val seekbarWidth = seekbar.x + seekbar.width
        if (tvWidth > seekbarWidth) {
            evTextView.x = seekbarWidth - evTextView.width
        }

        val value = "$progress EV"
        evTextView.text = value
    }

    private fun setNavigationResult() {
        val navController = findNavController()

        @Suppress("UNCHECKED_CAST")
        val pokemonStats = arguments?.get("pokemonStats") as Map<String, Int>
        @Suppress("UNCHECKED_CAST")
        val onPopBackStack = arguments?.get("popBack") as () -> Unit
        navController.previousBackStackEntry?.savedStateHandle?.set(
            "result",
            helper.computeStats(pokemonStats)
        )
        navController.popBackStack()
        onPopBackStack()
    }
}