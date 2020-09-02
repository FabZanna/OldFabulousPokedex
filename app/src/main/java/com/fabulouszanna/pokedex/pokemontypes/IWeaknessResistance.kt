package com.fabulouszanna.pokedex.pokemontypes

interface IWeaknessResistance {
    fun getWeaknesses(): List<String>
    fun getResistances(): List<String>?
    fun getImmunities(): List<String>?
}