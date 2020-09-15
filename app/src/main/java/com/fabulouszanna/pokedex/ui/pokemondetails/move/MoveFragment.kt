package com.fabulouszanna.pokedex.ui.pokemondetails.move

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabulouszanna.pokedex.databinding.FragmentMovesBinding
import com.fabulouszanna.pokedex.model.PokemonModel
import com.fabulouszanna.pokedex.repo.pokemonmove.PokemonMoveViewModel
import com.fabulouszanna.pokedex.utilities.extractColorResourceFromType
import com.fabulouszanna.pokedex.utilities.toPx
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MoveFragment(private val pokemon: PokemonModel) : Fragment() {
    private lateinit var binding: FragmentMovesBinding
    private val viewModel: PokemonMoveViewModel by viewModel { parametersOf(pokemon.name) }
    private lateinit var activeButton: RadioButton
    private lateinit var buttonFilterMapping: Map<RadioButton, String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMovesBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activeButton = binding.byLevelUp
        buttonFilterMapping =
            mapOf(binding.byLevelUp to "level_up", binding.byTM to "TM", binding.byEgg to "egg")
        setUpButtons()
        val layoutManager = LinearLayoutManager(context)

        viewModel.pokemonMove.observe(viewLifecycleOwner) { state ->
            when {
                state.pokemonAndMove.isEmpty() -> {
                    binding.noMove.visibility = View.VISIBLE
                    binding.movesList.visibility = View.GONE
                }
                else -> {
                    binding.noMove.visibility = View.GONE
                    binding.movesList.visibility = View.VISIBLE
                }
            }
            binding.movesList.apply {
                adapter = MoveAdapter(layoutInflater, state.pokemonAndMove, layoutManager)
                setLayoutManager(layoutManager)
            }
        }
    }

    private fun setUpButtons() {
        val color = extractColorResourceFromType(requireContext(), pokemon.type1)
        binding.radioGroup.children.forEach {
            when (val button = it as RadioButton) {
                activeButton -> button.apply {
                    background = GradientDrawable().apply { setColor(color) }
                    setTextColor(Color.WHITE)
                }
                else -> button.apply {
                    val gradient = GradientDrawable().apply { setStroke(1.toPx(), Color.BLACK) }
                    val layerDrawable = LayerDrawable(arrayOf(gradient))
                    val doubleStrokeSecondThirdButton =
                        activeButton == binding.byLevelUp && this == binding.byEgg
                    val doubleStrokeFirstSecondButton =
                        activeButton == binding.byEgg && this == binding.byTM
                    if (doubleStrokeFirstSecondButton || doubleStrokeSecondThirdButton) {
                        // Removes drawing a double stroke in between
                        layerDrawable.setLayerInset(0, (-1).toPx(), 0, 0, 0)
                    }
                    background = layerDrawable
                    setTextColor(Color.BLACK)
                    setOnClickListener { view -> onButtonClicked(view as RadioButton) }
                }
            }
        }
    }

    private fun onButtonClicked(button: RadioButton) {
        activeButton = button
        viewModel.moveLearnedBy(buttonFilterMapping.getValue(button))
        setUpButtons()
    }

}