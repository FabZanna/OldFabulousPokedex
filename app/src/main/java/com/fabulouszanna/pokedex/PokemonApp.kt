package com.fabulouszanna.pokedex

import android.app.Application
import com.fabulouszanna.pokedex.repo.MainDatabase
import com.fabulouszanna.pokedex.repo.pokemon.SinglePokemonViewModel
import com.fabulouszanna.pokedex.repo.ability.AbilityRepository
import com.fabulouszanna.pokedex.repo.ability.AbilityViewModel
import com.fabulouszanna.pokedex.repo.pokemon.PokemonRepository
import com.fabulouszanna.pokedex.repo.pokemon.PokemonViewModel
import com.fabulouszanna.pokedex.repo.pokemonmove.PokemonMoveRepository
import com.fabulouszanna.pokedex.repo.pokemonmove.PokemonMoveViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module


class PokemonApp : Application() {
    private val koinModule = module {
        single {
            PokemonRepository(
                get<MainDatabase>().pokemonDao()
            )
        }
        single { AbilityRepository(get<MainDatabase>().abilityDao()) }
        single { PokemonMoveRepository(get<MainDatabase>().pokemonMoveDao()) }
        single { MainDatabase.newInstance(androidContext()) }
        single(named("appScope")) { CoroutineScope(SupervisorJob()) }

        viewModel { PokemonViewModel(get()) }
        viewModel { (id: Int) -> SinglePokemonViewModel(get(), id) }
        viewModel { AbilityViewModel(get()) }
        viewModel { (pokemonName: String) -> PokemonMoveViewModel(get(), pokemonName) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@PokemonApp)
            modules(koinModule)
        }
    }
}