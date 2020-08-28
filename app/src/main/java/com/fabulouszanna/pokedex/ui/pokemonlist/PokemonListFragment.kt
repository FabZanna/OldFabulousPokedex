package com.fabulouszanna.pokedex.ui.pokemonlist

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fabulouszanna.pokedex.R
import com.fabulouszanna.pokedex.databinding.FragmentPokemonListBinding
import com.fabulouszanna.pokedex.model.PokemonModel
import com.fabulouszanna.pokedex.repo.PokemonViewModel
import com.fabulouszanna.pokedex.ui.filters.FilterDialog
import com.fabulouszanna.pokedex.utilities.RecyclerViewCustomItemDecoration
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment : Fragment() {
    private val viewModel: PokemonViewModel by viewModel()
    private lateinit var binding: FragmentPokemonListBinding
    private var fabClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        FragmentPokemonListBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.pokemon_search_menu, menu)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.searchPokemon).actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            isIconifiedByDefault = false
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(name: String?): Boolean {
                    searchByName(name)
                    return true
                }

                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }
            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PokemonAdapter(layoutInflater, onCardClicked = ::displayPokemon).apply {
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    binding.pokemonRv.scrollToPosition(0)
                }
            })
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
                else -> binding.emptyRecyclerView.visibility = View.GONE
            }
        }


        filter_fab.setOnClickListener {
            fabClicked = !fabClicked
            onFabClicked()
        }
    }

    private fun onFabClicked() {
        filter_fab.apply {
            if (fabClicked) {
                navToFilters()
                setImageResource(R.drawable.ic_close)
            } else {
                viewModel.filtered("", "all", "all")
                setImageResource(R.drawable.ic_filter)
            }
        }
    }

    private fun displayPokemon(model: PokemonModel) {
        findNavController().navigate(PokemonListFragmentDirections.displayPokemonDetails(model.id))
    }

    private fun navToFilters() {
        FilterDialog(
            onFilterClicked = ::onFilterClicked
        )
            .show(requireActivity().supportFragmentManager, "")
    }

    private fun searchByName(pokemonName: String?) {
        pokemonName?.let {
            viewModel.filtered(it, "all", "all")
        }
    }

    private fun onFilterClicked(gen: String, type: String) {
        viewModel.filtered("", gen, type)
    }
}