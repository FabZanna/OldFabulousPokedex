package com.fabulouszanna.pokedex.ui.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.fabulouszanna.pokedex.databinding.PokemonListBinding
import com.fabulouszanna.pokedex.repo.PokemonViewModel
import com.fabulouszanna.pokedex.ui.filters.FilterDialog
import com.fabulouszanna.pokedex.utilities.RecyclerViewCustomItemDecoration
import kotlinx.android.synthetic.main.pokemon_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class PokemonListFragment : Fragment() {
    private val viewModel: PokemonViewModel by viewModel()
    private lateinit var binding: PokemonListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = PokemonListBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PokemonAdapter(layoutInflater) {
            Toast.makeText(requireContext(), "Clicked ${it.name}", Toast.LENGTH_LONG).show()
        }

        binding.pokemonRv.apply {
            setAdapter(adapter)
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(
                RecyclerViewCustomItemDecoration(requireContext())
            )
        }

        viewModel.pokemons.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.pokemons)

            when {
                state.pokemons.isEmpty() -> {
                    binding.emptyRecyclerView.visibility = View.VISIBLE
                }
            }
        }
        binding.emptyRecyclerView.visibility = View.GONE

        filter_fab.setOnClickListener {
            navToFilters()
        }
    }

    private fun navToFilters() {
        FilterDialog{
            onFilterClicked(it)
        }
            .show(requireActivity().supportFragmentManager, "")
    }

    private fun onFilterClicked(gen: String) {
        val generation = gen.take(3).toLowerCase(Locale.ROOT) + gen.takeLast(1)
        viewModel.filterByGen(generation)
    }
}