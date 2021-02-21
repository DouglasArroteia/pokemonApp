package com.androidapp.douglas.pokemonapp.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androidapp.douglas.pokemonapp.api.repositories.PokemonRepositoryMocked
import com.androidapp.douglas.pokemonapp.loader.PokemonLoader
import com.androidapp.douglas.pokemonapp.view.observables.PokemonObservables
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonViewModelTest : AbstractViewModel() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var pokemonRepo: PokemonRepositoryMocked
    private lateinit var observables: PokemonObservables

    @Before
    fun setUp() {
        pokemonRepo = PokemonRepositoryMocked()
        observables = PokemonObservables()
    }

    /**
     * getPokemon method from PokemonViewModel should have an object with name set as Toothless
     */
    @Test
    fun getPokemonToothless() =
        runBlockingTest {
            this.launch {
                observables.apply {
                    val data = pokemonRepo.getPokemon(28)
                    val dataModel = handleResponse(data, observables)
                    dataModel?.let {
                        pokeDetailsObserver.value = it
                    }

                    pokeDetailsObserver.value?.name?.let { pokeName ->
                        assertThat(pokeName).isEqualTo("Toothless")
                    }
                }
            }
        }

    /**
     * getPokemon method from PokemonViewModel should have an object with name set as Toothless
     * and it's type should be dragon
     */
    @Test
    fun getPokemonTypeDragon() =
        runBlockingTest {

            this.launch {
                observables.apply {
                    val data = pokemonRepo.getPokemon(28)
                    val dataModel = handleResponse(data, this)
                    dataModel?.let {
                        pokeDetailsObserver.value = it
                    }
                    pokeDetailsObserver.value?.types?.let { types ->
                        assertThat(types[0].type.name).isEqualTo("dragon")
                    }
                }
            }
        }

    /**
     * getPokemon method from PokemonViewModel should have an object with a stats bigger than 255
     */
    @Test
    fun getPokemonStatsValue() =
        runBlockingTest {
            var correctValue = true
            this.launch {
                observables.apply {
                    val data = pokemonRepo.getPokemon(28)
                    val dataModel = handleResponse(data, this)
                    dataModel?.let {
                        pokeDetailsObserver.value = it
                        pokeDetailsObserver.value?.stats?.let { stats ->
                            stats.forEach { value ->
                                if (value.baseStat > 255f) correctValue = false
                            }
                        }
                    }

                    assertThat(correctValue).isTrue()
                }
            }
        }

    /**
     * getPokemon method from PokemonViewModel should never return null.
     */
    @Test
    fun getPokemonNotNull() = runBlockingTest {
        this.launch {
            observables.apply {
                val data = pokemonRepo.getPokemon(1)
                val dataModel = handleResponse(data, this)
                dataModel?.let { details ->
                    assertThat(details).isNotNull()
                }
            }
        }
    }


    private fun handleError(error: Throwable?) {
        error?.message?.let {
            observables.pokeLoaderObserver.value = PokemonLoader.DefaultError(it)
        }
    }
}