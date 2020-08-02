package com.fabulouszanna.pokedex

import android.app.Application
import com.fabulouszanna.pokedex.repo.PokemonDatabase
import com.fabulouszanna.pokedex.repo.PokemonRepository
import com.fabulouszanna.pokedex.repo.PokemonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module


class PokemonApp : Application() {
    private val koinModule = module {
        single {
            PokemonRepository(
                get<PokemonDatabase>().pokemonDao()
            )
        }
        single { PokemonDatabase.newInstance(androidContext()) }
        single(named("appScope")) { CoroutineScope(SupervisorJob()) }

        viewModel { PokemonViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PokemonApp)
            modules(koinModule)
        }
    }
}