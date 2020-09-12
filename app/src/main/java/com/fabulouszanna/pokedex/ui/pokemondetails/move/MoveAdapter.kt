package com.fabulouszanna.pokedex.ui.pokemondetails.move

import android.content.res.ColorStateList
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fabulouszanna.pokedex.R
import com.fabulouszanna.pokedex.databinding.MoveCardBinding
import com.fabulouszanna.pokedex.model.PokemonAndMoveModel
import com.fabulouszanna.pokedex.utilities.extractColorResourceFromType

class MoveAdapter(
    private val inflater: LayoutInflater,
    private val moves: List<PokemonAndMoveModel>
) : RecyclerView.Adapter<MoveAdapter.MoveRowHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveRowHolder =
        MoveRowHolder(MoveCardBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: MoveRowHolder, position: Int) {
        val item = moves[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = moves.size

    inner class MoveRowHolder(private val binding: MoveCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemonAndMove: PokemonAndMoveModel) {
            val type = pokemonAndMove.move.moveType
            val color = extractColorResourceFromType(binding.root.context, type)
            // val pokemonColor = extractColorResourceFromType(binding.root.context, pokemonType)
            binding.apply {
                moveName.text = pokemonAndMove.move.moveName
                // moveName.setBackgroundColor(pokemonColor)
                moveType.text = type
                learnedAt.text = pokemonAndMove.pokemonMove.moveLevelLearned
//                moveType.typePaintColor = extractColorResourceFromType(binding.root.context, type)
                // moveType.backgroundTintList = ColorStateList.valueOf(color)

                // moveCategory.setImageResource(category)
            }
            setMoveTypeBackgroundColor(color)
        }

        private fun setMoveTypeBackgroundColor(color: Int) {
            val background = binding.moveTypeColor.background as LayerDrawable
            background.findDrawableByLayerId(R.id.moveTypeColor)
                .setTintList(ColorStateList.valueOf(color))
        }
    }
}