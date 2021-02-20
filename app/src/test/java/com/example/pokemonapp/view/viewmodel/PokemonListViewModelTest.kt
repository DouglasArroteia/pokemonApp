package com.example.pokemonapp.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokemonapp.api.repositories.PokemonRepositoryMocked
import com.example.pokemonapp.loader.PokemonLoader
import com.example.pokemonapp.view.observables.PokeModel
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

    private lateinit var pokeListViewModel: PokemonListViewModel
    private lateinit var pokemonRepo: PokemonRepositoryMocked
    private lateinit var model: PokeModel

    @Before
    fun setUp() {
        pokeListViewModel = PokemonListViewModel(PokemonRepositoryMocked())
        pokemonRepo = PokemonRepositoryMocked()
        model = pokeListViewModel.pokeModel
    }

    /**
     * getPokemonList method from PokemonListViewModel should contain a list_test as value
     */
    @Test
    fun getPokemonListTest() =
        runBlockingTest {
            var value = ""
            this.launch {
                val responseData = pokemonRepo.getPokemonList(1)
                val responseDataModel = handleResponse(responseData, ::handleError)
                responseDataModel?.let {
                    model.pokeListObserver.value = it
                    model.pokeListObserver.value?.pokeList?.let { list ->
                        value = list[0].name
                    }
                }
            }
            assertThat(value).isEqualTo("list_test")
        }

    /**
     * getPokemonList method from PokemonListViewModel should never return null.
     */
    @Test
    fun getPokemonListNotNull() = runBlockingTest {
        this.launch {
            val data = pokemonRepo.getPokemonList(1)
            val dataModel = handleResponse(data, ::handleError)
            dataModel?.let {
                model.pokeListObserver.value = it
            }
        }
        val result = model.pokeListObserver.value
        assertThat(result).isNotNull()
    }

    private fun handleError(error: Throwable?) {
        error?.message?.let {
            pokeListViewModel.pokeModel.pokeLoaderObserver.value = PokemonLoader.DefaultError(it)
        }
    }
}