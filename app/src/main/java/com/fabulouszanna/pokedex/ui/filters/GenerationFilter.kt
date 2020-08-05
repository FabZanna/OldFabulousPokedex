package com.fabulouszanna.pokedex.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fabulouszanna.pokedex.databinding.GenerationFilterBinding
import com.fabulouszanna.pokedex.utilities.RecyclerViewCustomItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GenerationFilter(
    private val dialog: BottomSheetDialogFragment,
    private val onFilterClicked: (String) -> Unit
) : Fragment() {
    private lateinit var binding: GenerationFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = GenerationFilterBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GenerationFilterAdapter(dialog, onFilterClicked)
        binding.genRecyclerView.apply {
            setHasFixedSize(true)
            setAdapter(adapter)
            addItemDecoration(RecyclerViewCustomItemDecoration(requireContext()))
        }
    }


}