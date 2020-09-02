package com.fabulouszanna.pokedex.ui.pokemondetails.basestats

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fabulouszanna.pokedex.databinding.TypeWeaknessResistanceBinding
import com.fabulouszanna.pokedex.utilities.extractColorResourceFromType

class BaseStatsAdapter(
    private val inflater: LayoutInflater,
    private val weaknessList: Map<String, Float>
) : RecyclerView.Adapter<BaseStatsAdapter.BaseStatsViewHolder>() {
    private var containerWidth: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseStatsViewHolder {
        containerWidth = parent.measuredWidth
        return BaseStatsViewHolder(
            TypeWeaknessResistanceBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseStatsViewHolder, position: Int) {
        val currentWeakness = weaknessList.keys.toTypedArray()[position]
        val currentValue = weaknessList.getValue(currentWeakness)
        holder.bind(currentWeakness, currentValue)

        if (getItemViewType(position) == 1) {
            val params = holder.itemView.layoutParams as GridLayoutManager.LayoutParams
            val marginSize = containerWidth / 4
            params.apply {
                leftMargin = marginSize
                rightMargin = marginSize
            }
            holder.itemView.layoutParams = params
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (itemCount % 2 != 0 && itemCount - 1 == position) {
            return 1
        }
        return 0
    }

    override fun getItemCount(): Int = weaknessList.size

    inner class BaseStatsViewHolder(private val binding: TypeWeaknessResistanceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private fun formatValue(value: Float): String {
            if (value == 0.5f) {
                return "X \u00BD"
            }
            if (value == 0.25f) {
                return "X \u00BC"
            }
            return "X ${value.toInt()}"
        }

        fun bind(type: String, value: Float) {
            val color = extractColorResourceFromType(binding.root.context, type)
            binding.apply {
                weaknessResistanceType.text = type
                weaknessResistanceIntensity.text = formatValue(value)
                (weaknessResistanceType.parent as LinearLayout).backgroundTintList =
                    ColorStateList.valueOf(color)
            }
        }
    }
}