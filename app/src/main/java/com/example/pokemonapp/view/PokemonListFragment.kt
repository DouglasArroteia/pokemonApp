package com.example.pokemonapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.PokemonListFragmentBinding
import com.example.pokemonapp.loader.PokemonLoader
import com.example.pokemonapp.view.adapter.PokemonListAdapter
import com.example.pokemonapp.view.model.PokemonListViewModel
import com.example.pokemonapp.view.model.PokemonViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment : Fragment() {

    private val pokemonListViewModel by viewModel<PokemonListViewModel>()
    private val pokemonViewModel by sharedViewModel<PokemonViewModel>()

    private lateinit var listAdapter: PokemonListAdapter

    private lateinit var listBinding: PokemonListFragmentBinding

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

        pokemonListViewModel.pokeModel.pokeLoaderObserver.observe(
            viewLifecycleOwner,
            Observer {
                handleState(it)
            })
        pokemonListViewModel.pokeModel.pokeLoadedObserver.observe(
            viewLifecycleOwner,
            Observer { if (it) initComponents() })

        pokemonListViewModel.getPokemonList(REAL_POKEMONS)
    }

    private fun initComponents() {
        listBinding.recyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            COLUMNS_NUMBER,
            LinearLayoutManager.VERTICAL,
            false
        )

        listBinding.recyclerView.adapter = PokemonListAdapter(::navigateToDetailedFragment)
        val pokeList = pokemonListViewModel.pokeModel.pokeListObserver.value
        listAdapter = listBinding.recyclerView.adapter as PokemonListAdapter
        pokeList?.let {
            listAdapter.updateItems(pokeList.result)
        }
    }

    private fun handleState(state: PokemonLoader?) {
        val loadingDialog = LoadingDialog().getDialog(context)
        when (state) {
            is PokemonLoader.Loading -> {
                if (state.isLoading) {
                    loadingDialog.show()
                } else {
                    loadingDialog.dismiss()
                }
            }
            is PokemonLoader.GenericError -> {
                Toast.makeText(
                    context,
                    getString(R.string.generic_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun navigateToDetailedFragment(id: Int) {
        pokemonViewModel.pokeModel.pokeIdObserver.value = id
        findNavController().navigate(R.id.pokemon_details)
    }

    companion object {

        private val REAL_POKEMONS = 251

        private val COLUMNS_NUMBER = 2
    }
}