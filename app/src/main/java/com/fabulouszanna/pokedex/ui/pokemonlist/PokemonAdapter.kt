package com.fabulouszanna.pokedex.ui.pokemonlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.fabulouszanna.pokedex.databinding.PokemonCardBinding
import com.fabulouszanna.pokedex.model.PokemonModel

class PokemonAdapter(
    private val inflater: LayoutInflater,
    private val onCardClicked: (PokemonModel) -> Unit
) :
    ListAdapter<PokemonModel, PokemonRowHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonRowHolder =
        PokemonRowHolder(PokemonCardBinding.inflate(inflater, parent, false), onCardClicked)

    override fun onBindViewHolder(holder: PokemonRowHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private object DiffCallback : DiffUtil.ItemCallback<PokemonModel>() {
    override fun areItemsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean =
        oldItem.id == newItem.id
}