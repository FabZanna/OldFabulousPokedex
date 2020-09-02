package com.fabulouszanna.pokedex.ui.pokemondetails.basestats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fabulouszanna.pokedex.R
import com.fabulouszanna.pokedex.databinding.FragmentCalculateStatsBinding
import kotlinx.android.synthetic.main.calculate_stats_single_stat.view.*

class StatsCalculator : Fragment() {
    private lateinit var binding: FragmentCalculateStatsBinding
    private val helper = StatsCalculatorHelper()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCalculateStatsBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            cancel.setOnClickListener { findNavController().popBackStack() }
            submit.setOnClickListener { setNavigationResult() }
            levelPicker.apply {
                minValue = 1
                maxValue = 100
                setOnValueChangedListener { _, _, newValue -> helper.levelPicked = newValue }
            }
            naturePicker.apply {
                val natures = resources.getStringArray(R.array.natures)
                minValue = 0
                maxValue = natures.size - 1
                displayedValues = natures
                setOnValueChangedListener { _, _, newValue ->
                    helper.naturePicked = natures[newValue]
                }
            }
        }

        val stats = resources.getStringArray(R.array.stats)
        val container = binding.root.getChildAt(0) as LinearLayout
        stats.forEachIndexed { index, stat ->
            container.getChildAt(index).apply {
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
                    override fun onProgressChanged(seekbar: SeekBar, progress: Int, fromUser: Boolean) {
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

    private fun setEVText(seekbar: SeekBar, progress: Int, evTextView: TextView) {
        val offset =
            (seekbar.width - seekbar.paddingLeft - seekbar.paddingRight) * progress / seekbar.max
        evTextView.x = seekbar.x + seekbar.paddingLeft - seekbar.paddingRight + offset

        val value = "$progress EV"
        evTextView.text = value
    }

    private fun setNavigationResult() {
        val navController = findNavController()

        @Suppress("UNCHECKED_CAST")
        val pokemonStats = arguments?.get("pokemonStats") as Map<String, Int>
        navController.previousBackStackEntry?.savedStateHandle?.set(
            "result",
            helper.computeStats(pokemonStats)
        )
        navController.popBackStack()
    }
}