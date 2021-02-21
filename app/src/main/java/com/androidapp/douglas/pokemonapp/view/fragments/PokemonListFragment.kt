package com.example.pokemonapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.PokemonListFragmentBinding
import com.example.pokemonapp.extensions.isLandscape
import com.example.pokemonapp.view.adapter.PokemonListAdapter
import com.example.pokemonapp.view.observables.PokemonObservables
import com.example.pokemonapp.view.viewmodel.PokemonListViewModel
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment that handle the pokemon list
 */
class PokemonListFragment : BasePokemonFragment() {

    private val pokemonListViewModel by viewModel<PokemonListViewModel>()
    private val pokemonObservables: PokemonObservables by inject()

    private lateinit var listBinding: PokemonListFragmentBinding

    private lateinit var listAdapter: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        listBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.pokemon_list_fragment, container,
                false
            )
        return listBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonObservables.pokeLoadedObserver.observe(
            viewLifecycleOwner, {
                if (it) {
                    updatePokemonList()
                    updateSwipeDirectionIfNeeded()
                }
            })

        initComponents()
        pokemonListViewModel.getPokemonList()

        listBinding.swipeRefresh.setOnRefreshListener {
            if (listBinding.swipeRefresh.direction == SwipyRefreshLayoutDirection.TOP) {
                pokemonListViewModel.getPokemonList()
            } else {
                pokemonListViewModel.updatePokemonList()
            }
            listBinding.swipeRefresh.isRefreshing = false
        }
    }

    /**
     * Initialize fragment components
     */
    private fun initComponents() {
        context?.let { ctx ->
            listBinding.recyclerView.layoutManager = GridLayoutManager(
                requireContext(),
                if (ctx.resources.isLandscape()) LANDSCAPE_COLUMN_NUMBER else PORTRAIT_COLUMN_NUMBER,
                LinearLayoutManager.VERTICAL,
                false
            )
            listBinding.recyclerView.adapter = PokemonListAdapter(::navigateToDetailedFragment)
            listAdapter = listBinding.recyclerView.adapter as PokemonListAdapter
        }
    }

    /**
     * Updates the adapter with the new pokemons.
     */
    private fun updatePokemonList() {
        val pokeList = pokemonObservables.pokeListObserver.value
        pokeList?.let { list ->
            listAdapter.updateAdapter(list.pokeList)
        }
    }

    private fun updateSwipeDirectionIfNeeded() {
        if (listBinding.swipeRefresh.direction == SwipyRefreshLayoutDirection.TOP)
            listBinding.swipeRefresh.direction = SwipyRefreshLayoutDirection.BOTTOM
    }

    /**
     * Navigates to the DetailedFragment
     *
     * @param id the pokemon id to be detailed
     */
    private fun navigateToDetailedFragment(id: Int) {
        pokemonObservables.pokeIdObserver.value = id
        findNavController().navigate(R.id.pokemon_details)
    }

    companion object {
        private const val PORTRAIT_COLUMN_NUMBER = 1

        private const val LANDSCAPE_COLUMN_NUMBER = 2
    }
}