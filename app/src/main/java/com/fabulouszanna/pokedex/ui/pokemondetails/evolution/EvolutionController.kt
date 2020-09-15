package com.fabulouszanna.pokedex.ui.pokemondetails.evolution

import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import com.fabulouszanna.pokedex.R
import com.fabulouszanna.pokedex.databinding.EvolutionArrowBinding
import com.fabulouszanna.pokedex.databinding.EvolutionItemBinding
import com.fabulouszanna.pokedex.databinding.FragmentEvolutionBinding
import com.fabulouszanna.pokedex.utilities.inflate
import kotlinx.android.synthetic.main.evolution_arrow.view.*


data class EvolutionPokemon(
    val arrowLayout: EvolutionArrowBinding?,
    val pokemonLayout: EvolutionItemBinding
)

class EvolutionControllerTest(
    private val inflater: LayoutInflater,
    private val binding: FragmentEvolutionBinding
) {

    private val container = binding.container

    private var currentBindPokemon: EvolutionItemBinding? = null
    private var currentBindArrow: EvolutionArrowBinding? = null

    var isEevee = false
    private var firstEevee = true
    private var currentEevee = 0

    // Pointing up-left
    private var currentAngle = -135f


    private fun addArrow(): EvolutionArrowBinding? {
        if (currentBindPokemon == null) {
            return null
        }
        val arrow = EvolutionArrowBinding.inflate(inflater, container, true)
        arrow.root.id = View.generateViewId()
        (arrow.root.layoutParams as ConstraintLayout.LayoutParams).apply {
            startToEnd = currentBindPokemon!!.root.id
            topToTop = currentBindPokemon!!.root.id
            bottomToBottom = currentBindPokemon!!.root.id
        }
        (currentBindPokemon!!.root.layoutParams as ConstraintLayout.LayoutParams).apply {
            endToStart = arrow.root.id
        }
        currentBindArrow = arrow
        return arrow
    }

    fun addPokemon(): EvolutionPokemon {
        if (isEevee) {
            return addEevee()
        }
        val arrowLayout = addArrow()
        val item = EvolutionItemBinding.inflate(inflater, binding.container, true)
        item.root.id = View.generateViewId()
        (item.root.layoutParams as ConstraintLayout.LayoutParams).apply {
            when (currentBindPokemon) {
                null -> {
                    bottomToBottom = container.id
                    endToEnd = container.id
                    startToStart = container.id
                    topToTop = container.id
                }
                else -> {
                    startToEnd = currentBindArrow!!.root.id
                    topToTop = container.id
                    bottomToBottom = container.id
                    endToEnd = container.id
                }
            }

        }
        currentBindArrow?.root?.layoutParams?.let {
            val params = it as ConstraintLayout.LayoutParams
            params.endToStart = item.root.id
        }
        currentBindPokemon = item
        return EvolutionPokemon(
            arrowLayout,
            item
        )
    }

    private fun addEevee(): EvolutionPokemon {
        if (firstEevee) {
            binding.root.removeAllViews()
            binding.root.inflate(R.layout.eevee_evolution, true)
            firstEevee = false
        }
        val container = binding.root.getChildAt(0) as ConstraintLayout
        val arrowLayout: EvolutionArrowBinding?
        val itemLayout: EvolutionItemBinding
        when (currentEevee) {
            0 -> {
                arrowLayout = null
                itemLayout = EvolutionItemBinding.bind(container.getChildAt(0))
                currentEevee++
            }
            else -> {
                arrowLayout = EvolutionArrowBinding.bind(container.getChildAt(currentEevee))
                arrowLayout.arrow.rotation = currentAngle
                itemLayout = EvolutionItemBinding.bind(container.getChildAt(currentEevee + 1))
                currentAngle += 45f
                currentEevee += 2
            }
        }
        return EvolutionPokemon(
            arrowLayout,
            itemLayout
        )
    }

    fun adjustSplitEvolutions() {
        if (isEevee) {
            return
        }
        val arrowLayouts = mutableListOf<ConstraintLayout>()
        val pokemonLayouts = mutableListOf<ConstraintLayout>()
        container.forEach { view ->
            val viewGroup = view as ConstraintLayout
            if (viewGroup.getChildAt(0).id == R.id.arrow) {
                arrowLayouts.add(viewGroup)
            } else {
                pokemonLayouts.add(viewGroup)
            }
        }

        val last2Arrow = arrowLayouts.takeLast(2)
        val last3Pokemon = pokemonLayouts.takeLast(3)
        last2Arrow[0].arrow.rotation = -30f
        (last2Arrow[0].layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomToBottom = ConstraintLayout.LayoutParams.UNSET
            bottomToTop = last2Arrow[1].id
            endToStart = last3Pokemon[1].id
            startToEnd = last3Pokemon[0].id
            topToTop = container.id
        }
        last2Arrow[1].arrow.rotation = 30f
        (last2Arrow[1].layoutParams as ConstraintLayout.LayoutParams).apply {
            topToTop = ConstraintLayout.LayoutParams.UNSET
            bottomToBottom = container.id
            startToEnd = last3Pokemon[0].id
            topToBottom = last2Arrow[0].id
            endToStart = last3Pokemon[2].id
        }

        (last3Pokemon[1].layoutParams as ConstraintLayout.LayoutParams).apply {
            endToStart = ConstraintLayout.LayoutParams.UNSET
            bottomToBottom = last2Arrow[0].id
            endToEnd = container.id
            startToEnd = last2Arrow[0].id
        }
        (last3Pokemon[2].layoutParams as ConstraintLayout.LayoutParams).apply {
            topToTop = last2Arrow[1].id
            endToEnd = container.id
            startToEnd = last2Arrow[1].id
        }
    }

}