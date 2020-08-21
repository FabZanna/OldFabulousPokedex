package com.fabulouszanna.pokedex.ui.filters

import android.content.Context
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeFilterViewHolder {
        val inflatedView = parent.inflate(R.layout.type_filter_card)
        return TypeFilterViewHolder(inflatedView, onTypeFilterClicked)
    }

    override fun getItemCount(): Int = types.size

    override fun onBindViewHolder(holder: TypeFilterViewHolder, position: Int) {
        val typeSelected = types[position]
        holder.bind(typeSelected)
    }

    inner class TypeFilterViewHolder(
        itemView: View,
        private val onTypeFilterClicked: (String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(type: String) {
            val pokemonColorFromType = extractColorResourceFromType(
                context, type.toLowerCase(Locale.ROOT)
            )
            itemView.type.text = type
            itemView.type.background.setColorFilter(pokemonColorFromType, PorterDuff.Mode.SRC_IN)
            itemView.type.setOnClickListener {
                onTypeFilterClicked(type.capitalize())
            }
        }
    }
}