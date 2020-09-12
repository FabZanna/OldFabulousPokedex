package com.fabulouszanna.pokedex.repo.ability

import com.fabulouszanna.pokedex.model.AbilityModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AbilityRepository(private val abilityDAO: AbilityEntity.abilityDAO) {
    fun getAbilityByName(abilityName: String): Flow<AbilityModel> =
        abilityDAO.getAbilityByName(abilityName).map { it.toModel() }
}