package com.example.pokemonapp.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokemonapp.api.repositories.PokemonRepositoryMocked
import com.example.pokemonapp.loader.PokemonLoader
import com.example.pokemonapp.view.observables.PokemonObservables
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

    private lateinit var pokeViewModel: PokemonViewModel
    private lateinit var pokemonRepo: PokemonRepositoryMocked
    private lateinit var model: PokemonObservables

    @Before
    fun setUp() {
        pokeViewModel = PokemonViewModel(PokemonRepositoryMocked())
        pokemonRepo = PokemonRepositoryMocked()
        model = pokeViewModel.pokeModel
    }

    /**
     * getPokemon method from PokemonViewModel should have an object with name set as Toothless
     */
    @Test
    fun getPokemonToothless() =
        runBlockingTest {
            var name = ""
            this.launch {
                val data = pokemonRepo.getPokemon(28)
                val dataModel = handleResponse(data, ::handleError)
                dataModel?.let {
                    model.pokeDetailsObserver.value = it
                    model.pokeDetailsObserver.value?.name?.let { pokeName ->
                        name = pokeName
                    }
                }
            }
            assertThat(name).isEqualTo("Toothless")
        }

    /**
     * getPokemon method from PokemonViewModel should have an object with name set as Toothless
     * and it's type should be dragon
     */
    @Test
    fun getPokemonTypeDragon() =
        runBlockingTest {
            var type = ""
            this.launch {
                val data = pokemonRepo.getPokemon(28)
                val dataModel = handleResponse(data, ::handleError)
                dataModel?.let {
                    model.pokeDetailsObserver.value = it
                    model.pokeDetailsObserver.value?.types?.let { types ->
                        type = types[0].type.name
                    }
                }
            }
            assertThat(type).isEqualTo("dragon")
        }

    /**
     * getPokemon method from PokemonViewModel should have an object with a stats bigger than 255
     */
    @Test
    fun getPokemonStatsValue() =
        runBlockingTest {
            var correctValue = true
            this.launch {
                val data = pokemonRepo.getPokemon(28)
                val dataModel = handleResponse(data, ::handleError)
                dataModel?.let {
                    model.pokeDetailsObserver.value = it
                    model.pokeDetailsObserver.value?.stats?.let { stats ->
                        stats.forEach { value ->
                            if (value.baseStat > 255f) correctValue = false
                        }
                    }
                }
            }
            assertThat(correctValue).isTrue()
        }

    /**
     * getPokemon method from PokemonViewModel should never return null.
     */
    @Test
    fun getPokemonNotNull() = runBlockingTest {
        val model = pokeViewModel.pokeModel
        this.launch {
            val data = pokemonRepo.getPokemon(1)
            val dataModel = handleResponse(data, ::handleError)
            dataModel?.let {
                model.pokeDetailsObserver.value = it
            }
        }
        val result = model.pokeDetailsObserver.value
        assertThat(result).isNotNull()
    }


    private fun handleError(error: Throwable?) {
        error?.message?.let {
            pokeViewModel.pokeModel.pokeLoaderObserver.value = PokemonLoader.DefaultError(it)
        }
    }
}