package com.fabulouszanna.pokedex.ui.ability

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fabulouszanna.pokedex.databinding.AbilityBottomSheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AbilityFragment(private val abilityName: String, private val color: Int) : BottomSheetDialogFragment() {
    private lateinit var binding: AbilityBottomSheetFragmentBinding
    private val abilityViewModel: AbilityViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = AbilityBottomSheetFragmentBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        abilityViewModel.getAbilityByName(abilityName).observe(viewLifecycleOwner) { ability ->
            binding.apply {
                abilityName.text = ability.abilityName
                gameDescription.text = ability.gameDescription
                abilityEffect.text = ability.effect

                abilityName.backgroundTintList = ColorStateList.valueOf(color)
            }
        }
    }
}