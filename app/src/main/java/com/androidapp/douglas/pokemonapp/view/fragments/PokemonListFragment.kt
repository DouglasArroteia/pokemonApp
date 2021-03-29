package com.androidapp.douglas.pokemonapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.douglas.pokemonapp.R
import com.androidapp.douglas.pokemonapp.databinding.PokemonListFragmentBinding
import com.androidapp.douglas.pokemonapp.extensions.isLandscape
import com.androidapp.douglas.pokemonapp.view.adapter.PokemonListAdapter
import com.androidapp.douglas.pokemonapp.view.observables.PokemonObservables
import com.androidapp.douglas.pokemonapp.view.viewmodel.PokemonListViewModel
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
            PokemonListFragmentBinding.inflate(
                layoutInflater
            )
        return listBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonObservables.pokeLoadedObserver.observe(
            viewLifecycleOwner, {
                if (it) {
                    updatePokemonList()
                }
            })

        initComponents()
        pokemonListViewModel.getPokemonList()
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
            listBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        pokemonListViewModel.updatePokemonList()
                    }
                }
            })
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