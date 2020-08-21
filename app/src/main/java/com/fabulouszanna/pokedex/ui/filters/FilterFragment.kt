package com.fabulouszanna.pokedex.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fabulouszanna.pokedex.databinding.FilterRecyclerviewBinding
import com.fabulouszanna.pokedex.utilities.RecyclerViewCustomItemDecoration

class FilterFragment(
    private val adapterType: String,
    private val onFilterClicked: (String) -> Unit
) : Fragment() {
    private lateinit var binding: FilterRecyclerviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FilterRecyclerviewBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = when (adapterType) {
            "generation" -> GenerationFilterAdapter(requireContext(), onFilterClicked)
            else -> TypeFilterAdapter(requireContext(), onFilterClicked)
        }

        binding.filterRecyclerView.apply {
            setHasFixedSize(true)
            setAdapter(adapter)
            addItemDecoration(RecyclerViewCustomItemDecoration(requireContext()))
        }
    }


}