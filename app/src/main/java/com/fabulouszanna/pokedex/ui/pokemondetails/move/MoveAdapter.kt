package com.fabulouszanna.pokedex.ui.pokemondetails.move

import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fabulouszanna.pokedex.R
import com.fabulouszanna.pokedex.databinding.MoveCardBinding
import com.fabulouszanna.pokedex.databinding.MoveSingleDetailBinding
import com.fabulouszanna.pokedex.model.PokemonAndMoveModel
import com.fabulouszanna.pokedex.utilities.extractColorResourceFromType
import com.fabulouszanna.pokedex.utilities.toPx

class MoveAdapter(
    private val inflater: LayoutInflater,
    private val moves: List<PokemonAndMoveModel>,
    private val layoutManager: LinearLayoutManager
) : RecyclerView.Adapter<MoveAdapter.MoveRowHolder>() {

    private val longestMoveType = moves.maxByOrNull { it.move.moveType.length }?.move?.moveType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveRowHolder =
        MoveRowHolder(MoveCardBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: MoveRowHolder, position: Int) {
        val item = moves[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = moves.size

    inner class MoveRowHolder(private val binding: MoveCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private fun TextView.adjustWidthToLongestMoveType(text: String) {
            this.text = when (longestMoveType) {
                "Flying" -> "Normal"
                else -> longestMoveType
            }
            post {
                val width = measuredWidth
                this.text = text
                this.layoutParams.width = width
            }
        }

        private fun MoveSingleDetailBinding.bindMoveSingleDetail(
            name: String,
            detail: String,
            justifyLeft: Boolean = false
        ) {

            this.detailName.text = name
            this.detailValue.text = detail
            this.root.applyBackgroundDrawable()
            if (justifyLeft) {
                this.detailValue.gravity = Gravity.START
            }
        }

        private fun View.applyBackgroundDrawable() {
            val color = ContextCompat.getColor(binding.root.context, R.color.lightGray)
            val backgroundDrawable = GradientDrawable().apply { setStroke(1.toPx(), color) }
            this.background = backgroundDrawable
        }

        private fun bindMoveDetails(pokemonAndMove: PokemonAndMoveModel) {
            val category = when (pokemonAndMove.move.category) {
                "Physical" -> R.drawable.move_physical
                "Special" -> R.drawable.move_special
                "Status" -> R.drawable.move_status
                else -> throw IllegalArgumentException("Move category ${pokemonAndMove.move.category} does not exist")
            }
            binding.apply {
                moveCategory.setImageResource(category)
                (moveCategory.parent as FrameLayout).applyBackgroundDrawable()
                movePower.bindMoveSingleDetail("Power", pokemonAndMove.move.power)
                moveAccuracy.bindMoveSingleDetail("Accuracy", pokemonAndMove.move.accuracy)
                movePP.bindMoveSingleDetail("PP", pokemonAndMove.move.pp)
                moveDescription.bindMoveSingleDetail(
                    "Description",
                    pokemonAndMove.move.description,
                    true
                )
                moveEffect.bindMoveSingleDetail("Effect", pokemonAndMove.move.effect, true)
            }
        }

        fun bind(pokemonAndMove: PokemonAndMoveModel, position: Int) {
            val context = binding.root.context
            val type = pokemonAndMove.move.moveType
            val color = extractColorResourceFromType(context, type)
            val levelLearned: String = when (pokemonAndMove.pokemonMove.moveLevelLearned) {
                "\u2014" -> pokemonAndMove.pokemonMove.moveLevelLearned
                else -> "LV ${pokemonAndMove.pokemonMove.moveLevelLearned}"
            }
            val tmLearned = "TM ${pokemonAndMove.pokemonMove.moveTM}"
            binding.apply {
                moveName.text = pokemonAndMove.move.moveName
                moveType.adjustWidthToLongestMoveType(type)
                when (pokemonAndMove.pokemonMove.learnMethod) {
                    "level_up" -> {
                        learnedString.text = context.getString(R.string.learned_at)
                        learnedAt.text = levelLearned
                    }
                    "TM" -> {
                        learnedString.text = context.getString(R.string.learned_by)
                        learnedAt.text = tmLearned
                    }
                    "egg" -> {
                        learnedString.text = context.getString(R.string.learned_by)
                        learnedAt.text = "Egg"
                    }
                }
                moveTypeColor.obliqueColor = color

                cardView.setOnClickListener {
                    if (moveDetails.visibility == View.GONE) {
                        moveDetails.visibility = View.VISIBLE
                        layoutManager.scrollToPositionWithOffset(position, 0)
                    } else {
                        moveDetails.visibility = View.GONE
                    }
                }
            }

            bindMoveDetails(pokemonAndMove)
        }
    }
}