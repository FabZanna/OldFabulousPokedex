package com.fabulouszanna.pokedex.ui.pokemonlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fabulouszanna.pokedex.databinding.PokemonCardBinding
import com.fabulouszanna.pokedex.model.PokemonModel
import com.fabulouszanna.pokedex.utilities.extractColorResourceFromType
import com.fabulouszanna.pokedex.utilities.setPokemonSprite

class PokemonRowHolder(
    private val binding: PokemonCardBinding,
    private val onCardClicked: (PokemonModel) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(model: PokemonModel) {
        val pokemonColorFromType = extractColorResourceFromType(binding.root.context, model.type1)

        binding.apply {
            pokemonName.text = model.name
            pokemonType1.text = model.type1
            pokemonType2.text = model.type2
            pokemonType2.visibility = if (model.type2 != null) View.VISIBLE else View.GONE
            pokemonCard.setOnClickListener { onCardClicked(model) }

            setPokemonSprite(binding.root.context, model.imgUrl, binding.pokemonImg)

            pokemonCard.setCardBackgroundColor(pokemonColorFromType)
        }
    }
}