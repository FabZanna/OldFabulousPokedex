package com.fabulouszanna.pokedex.ui.pokemondetails.move

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabulouszanna.pokedex.databinding.FragmentMovesBinding
import com.fabulouszanna.pokedex.model.PokemonModel
import com.fabulouszanna.pokedex.repo.pokemonmove.PokemonMoveViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MoveFragment(private val pokemon: PokemonModel) : Fragment() {
    private lateinit var binding: FragmentMovesBinding
    private val viewModel: PokemonMoveViewModel by viewModel { parametersOf(pokemon.name) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMovesBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.pokemonMove.observe(viewLifecycleOwner) { state ->
            binding.movesList.apply {
                adapter = MoveAdapter(layoutInflater, state.pokemonAndMove)
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
    }

}