package com.fabulouszanna.pokedex.ui.pokemondetails.evolution

import android.view.ViewGroup
import com.fabulouszanna.pokedex.R
import com.fabulouszanna.pokedex.databinding.EvolutionArrowBinding
import com.fabulouszanna.pokedex.databinding.EvolutionItemBinding
import com.fabulouszanna.pokedex.databinding.FragmentEvolutionBinding
import com.fabulouszanna.pokedex.utilities.inflate
import kotlinx.android.synthetic.main.evolution_arrow.view.*

data class EvolutionComponents(
    val arrowLayout: EvolutionArrowBinding?,
    val pokemonLayout: EvolutionItemBinding
)

class EvolutionController(
    private val binding: FragmentEvolutionBinding
) {
    private var currentEvolutionComponents = 0
    private var isSplitEvolution = false
    private var isEevee = false
    private lateinit var root: ViewGroup

    fun inflateLayout(numberOfEvolutions: Int, isSplit: Boolean, isEevee: Boolean = false) {
        isSplitEvolution = isSplit
        this.isEevee = isEevee
        when {
            isEevee -> binding.root.inflate(R.layout.evolution_eevee, true)
            numberOfEvolutions == 1 -> binding.root.inflate(R.layout.evolution_zero, true)
            numberOfEvolutions == 2 -> binding.root.inflate(R.layout.evolution_one, true)
            numberOfEvolutions == 3 && isSplit -> binding.root.inflate(
                R.layout.evolution_one_split,
                true
            )
            numberOfEvolutions == 3 && !isSplit -> binding.root.inflate(
                R.layout.evolution_two,
                true
            )
            else -> binding.root.inflate(R.layout.evolution_two_split, true)
        }
        root = binding.root.getChildAt(0) as ViewGroup
    }

    fun getEvolutionComponents(): EvolutionComponents {
        val arrowLayout: EvolutionArrowBinding?
        val pokemonLayout: EvolutionItemBinding
        when (currentEvolutionComponents) {
            0 -> {
                arrowLayout = null
                pokemonLayout = EvolutionItemBinding.bind(root.getChildAt(0))
                currentEvolutionComponents++
            }
            else -> {
                arrowLayout =
                    EvolutionArrowBinding.bind(root.getChildAt(currentEvolutionComponents))
                pokemonLayout =
                    EvolutionItemBinding.bind(root.getChildAt(currentEvolutionComponents + 1))
                currentEvolutionComponents += 2
            }
        }
        return EvolutionComponents(arrowLayout, pokemonLayout)
    }

    fun adjustArrowRotation() {
        if (!isSplitEvolution) {
            return
        }
        if (isEevee)
            adjustEeveeArrowRotation()
        else {
            val lastIndex = root.childCount - 1
            val last2Arrow =
                listOf(root.getChildAt(lastIndex - 1), root.getChildAt(lastIndex - 3))
            last2Arrow[0].arrow.rotation = 30f
            last2Arrow[1].arrow.rotation = -30f
        }
    }

    private fun adjustEeveeArrowRotation() {
        // Pointing top-left
        var angle = -135f
        val firstArrowIndex = 1
        for (index in firstArrowIndex until root.childCount step 2) {
            val arrow = root.getChildAt(index)
            arrow.arrow.rotation = angle
            angle += 45f
        }
    }
}