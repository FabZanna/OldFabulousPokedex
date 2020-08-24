package com.fabulouszanna.pokedex.ui.filters

import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fabulouszanna.pokedex.R
import com.fabulouszanna.pokedex.utilities.extractColorResourceFromType
import com.fabulouszanna.pokedex.utilities.inflate
import kotlinx.android.synthetic.main.type_filter_card.view.*
import java.util.*

class TypeFilterAdapter(
    private val context: Context,
    private val onTypeFilterClicked: (String) -> Unit
) : RecyclerView.Adapter<TypeFilterAdapter.TypeFilterViewHolder>() {
    private val types = context.resources.getStringArray(R.array.types)
    private val typesSelected = MutableList(types.size) { false }
    private var selectedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeFilterViewHolder {
        val inflatedView = parent.inflate(R.layout.type_filter_card)
        return TypeFilterViewHolder(inflatedView, onTypeFilterClicked)
    }

    override fun getItemCount(): Int = types.size

    override fun onBindViewHolder(holder: TypeFilterViewHolder, position: Int) {
        val typeSelected = types[position]
        holder.bind(typeSelected, position)

        val selectedColor = ContextCompat.getColor(context, R.color.colorAccent)
        val transparent = ContextCompat.getColor(context, android.R.color.transparent)
        val card = holder.itemView.filterCard as CardView
        if (selectedPosition == position) {
            if (typesSelected[position]) {
                card.setCardBackgroundColor(selectedColor)
                holder.itemView.selectedFilter.visibility = View.VISIBLE
            } else {
                card.setCardBackgroundColor(transparent)
                holder.itemView.selectedFilter.visibility = View.INVISIBLE
            }
        } else {
            card.setCardBackgroundColor(transparent)
            holder.itemView.selectedFilter.visibility = View.INVISIBLE
        }
    }

    inner class TypeFilterViewHolder(
        itemView: View,
        private val onTypeFilterClicked: (String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(type: String, position: Int) {
            val pokemonColorFromType = extractColorResourceFromType(
                context, type.toLowerCase(Locale.ROOT)
            )
            itemView.type.text = type
            itemView.type.background.setColorFilter(pokemonColorFromType, PorterDuff.Mode.SRC_IN)
            itemView.type.setOnClickListener {
                typesSelected.forEachIndexed { index, _ ->
                    if (index == position) typesSelected[index] = !typesSelected[index]
                    else typesSelected[index] = false
                }
                selectedPosition = position

                val filter = if (typesSelected.all { !it }) "all" else type.capitalize()
                onTypeFilterClicked(filter)
                notifyDataSetChanged()
            }
        }
    }
}