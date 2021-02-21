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
class PokemonListViewModelTest : AbstractViewModel() {

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
     * getPokemonList method from PokemonListViewModel should contain a list_test as value
     */
    @Test
    fun getPokemonListTest() =
        runBlockingTest {

            this.launch {
                observables.apply {
                    val responseData = pokemonRepo.getPokemonList(1)
                    val responseDataModel = handleResponse(responseData, this)
                    responseDataModel?.let {
                        pokeListObserver.value = it
                    }

                    pokeListObserver.value?.pokeList?.let { list ->
                        assertThat(list[0].name).isEqualTo("list_test")
                    }
                }
            }
        }

    /**
     * getPokemonList method from PokemonListViewModel should never return null.
     */
    @Test
    fun getPokemonListNotNull() = runBlockingTest {
        this.launch {
            observables.apply {
                val data = pokemonRepo.getPokemonList(1)
                val dataModel = handleResponse(data, this)
                dataModel?.let {
                    pokeListObserver.value = it
                }

                assertThat(observables.pokeListObserver.value).isNotNull()
            }
        }
    }

    private fun handleError(error: Throwable?) {
        error?.message?.let {
            observables.pokeLoaderObserver.value = PokemonLoader.DefaultError(it)
        }
    }
}