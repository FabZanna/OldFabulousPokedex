package com.fabulouszanna.pokedex.ui.pokemondetails.basestats

import android.widget.SeekBar
import com.fabulouszanna.pokedex.model.NatureModel
import kotlin.math.floor
import kotlin.math.roundToInt

class StatsCalculatorHelper {
    private val totalEV = 508
    var levelPicked = 0
    var naturePicked = "Adamant"

    private val storedIVs = mutableListOf(0, 0, 0, 0, 0, 0)
    private val storedEVs = mutableListOf(0, 0, 0, 0, 0, 0)

    fun storeIVs(index: Int, value: Int) {
        storedIVs[index] = value
    }

    fun getProgressWithStep(progress: Int): Int {
        val step = 4
        return (progress.toFloat() / step).roundToInt() * step
    }

    fun limitSeekBars(seekbar: SeekBar, progress: Int) {
        val tag = seekbar.tag as Int
        val storedProgress = storedEVs[tag]
        if (progress > storedProgress) {
            val remaining = totalEV - storedEVs.sum()
            when {
                remaining == 0 -> seekbar.progress = storedProgress
                storedProgress + remaining >= progress -> storedEVs[tag] = progress
                else -> storedEVs[tag] = storedProgress + remaining
            }
        } else {
            storedEVs[tag] = progress
        }
    }

    fun computeStats(pokemonStats: Map<String, Int>): Map<String, Int> {
        val computedStats = mutableMapOf<String, Int>()
        val increasedStat = NatureModel.getIncreasedStat(naturePicked)
        val decreasedStat = NatureModel.getDecreasedStat(naturePicked)

        var i = 0
        pokemonStats.forEach { (stat, baseValue) ->
            val baseCalculation =
                floor(((2 * baseValue + storedIVs[i] + floor(storedEVs[i] / 4f)) * levelPicked) / 100f).toInt()
            when (stat) {
                "HP" -> computedStats[stat] = baseCalculation + levelPicked + 10
                increasedStat -> computedStats[stat] = floor((baseCalculation + 5) * 1.1).toInt()
                decreasedStat -> computedStats[stat] = floor((baseCalculation + 5) * 0.9).toInt()
                else -> computedStats[stat] = baseCalculation + 5
            }
            i++
        }
        return computedStats
    }
}