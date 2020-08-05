package com.fabulouszanna.pokedex.ui.filters

import android.util.Log
import android.view.View
import android.view.ViewGroup
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
    val generation: String
)

class GenerationFilterAdapter(
    private val dialog: BottomSheetDialogFragment,
    private val onFilterClicked: (String) -> Unit
) :
    RecyclerView.Adapter<GenerationFilterAdapter.GenFilterViewHolder>() {

    private val generationList = initializeFilter()

    private fun initializeFilter(): List<PokemonGenFilter> {
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
                    "Generation $i"
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
        Log.d("POKEMON", generationList[position].url1)
        holder.bind(generationFilter)
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
                onFilterClicked(model.generation)
                dialog.dismiss()
            }
        }
    }
}