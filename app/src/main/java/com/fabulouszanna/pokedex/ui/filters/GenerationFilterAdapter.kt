package com.fabulouszanna.pokedex.ui.filters

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fabulouszanna.pokedex.R
import com.fabulouszanna.pokedex.utilities.inflate
import com.fabulouszanna.pokedex.utilities.setPokemonSprite
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.generation_filter_card.view.*

data class PokemonGenFilter(
    val url1: String,
    val url2: String,
    val url3: String,
    val generation: String,
    var isSelected: Boolean = false,
    val tag: Int
)

class GenerationFilterAdapter(
    private val dialog: BottomSheetDialogFragment,
    private val onFilterClicked: (String) -> Unit
) :
    RecyclerView.Adapter<GenerationFilterAdapter.GenFilterViewHolder>() {

    private val generationList = initializeFilter()
    private var selectedPosition: Int? = null

    private fun initializeFilter(): MutableList<PokemonGenFilter> {
        val list = mutableListOf<PokemonGenFilter>()
        val numberOfGenerations = 8
        for (i in 1..numberOfGenerations) {
            val firstGenerationPokemon = when (i) {
                1 -> 1
                2 -> 152
                3 -> 252
                4 -> 387
                5 -> 495
                6 -> 650
                7 -> 722
                8 -> 810
                else -> 0
            }
            list.add(
                PokemonGenFilter(
                    "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${firstGenerationPokemon.toString()
                        .padStart(3, '0')}.png",
                    "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${(firstGenerationPokemon + 3).toString()
                        .padStart(3, '0')}.png",
                    "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${(firstGenerationPokemon + 6).toString()
                        .padStart(3, '0')}.png",
                    "Generation $i",
                    tag = i - 1
                )
            )
        }
        return list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenFilterViewHolder {
        val inflatedView =
            parent.inflate(R.layout.generation_filter_card)
        return GenFilterViewHolder(inflatedView, onFilterClicked)
    }

    override fun onBindViewHolder(holder: GenFilterViewHolder, position: Int) {
        val generationFilter = generationList[position]
        val selectedColor = ContextCompat.getColor(dialog.requireContext(), R.color.colorAccent)
        holder.bind(generationFilter)

        val card = holder.itemView as CardView
        if (selectedPosition == position) {
            if (generationFilter.isSelected) {
                card.setCardBackgroundColor(selectedColor)
            } else {
                card.setCardBackgroundColor(Color.WHITE)
            }
        } else {
            card.setCardBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int = generationList.size

    inner class GenFilterViewHolder(itemView: View, private val onFilterClicked: (String) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(model: PokemonGenFilter) {
            setPokemonSprite(itemView.context, model.url1, itemView.url1)
            setPokemonSprite(itemView.context, model.url2, itemView.url2)
            setPokemonSprite(itemView.context, model.url3, itemView.url3)
            itemView.generation.text = model.generation

            itemView.rootView.setOnClickListener {
                generationList.forEach {
                    if (it.tag == model.tag) model.isSelected =
                        !model.isSelected else it.isSelected = false
                }
                selectedPosition = model.tag

                val filter = if (generationList.all { !it.isSelected }) "all" else model.generation
                onFilterClicked(filter)
                notifyDataSetChanged()
            }
        }
    }
}