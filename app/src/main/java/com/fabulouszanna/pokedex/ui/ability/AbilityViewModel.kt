package com.fabulouszanna.pokedex.ui.ability

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fabulouszanna.pokedex.model.AbilityModel
import com.fabulouszanna.pokedex.repo.ability.AbilityRepository

data class AbilityViewState(
    val ability: AbilityModel
)

class AbilityViewModel(
    private val repository: AbilityRepository
) : ViewModel() {
    fun getAbilityByName(abilityName: String): LiveData<AbilityModel> =
        repository.getAbilityByName(abilityName).asLiveData()
    //repository.getAbilityByName(abilityName).map { AbilityViewState(it) }.asLiveData()
}