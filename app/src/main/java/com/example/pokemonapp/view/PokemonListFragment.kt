package com.example.pokemonapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.PokemonListFragmentBinding
import com.example.pokemonapp.loader.PokemonLoader
import com.example.pokemonapp.view.adapter.PokemonListAdapter
import com.example.pokemonapp.view.model.PokeModel
import com.example.pokemonapp.view.model.PokemonListViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PokemonListFragment : Fragment() {

    private val pokemonListViewModel by sharedViewModel<PokemonListViewModel>()
    private val pokeModel: PokeModel by inject()

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

        pokeModel.pokeLoaderObserver.observe(
            viewLifecycleOwner,
            Observer { handleState(it) })
        pokeModel.pokeLoadedObserver.observe(
            viewLifecycleOwner,
            Observer { if (it) initComponents() })

        pokemonListViewModel.getPokemonList(FIRST_GEN_POKE)
    }

    private fun initComponents() {
        listBinding.recyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            COLUMNS_NUMBER,
            LinearLayoutManager.VERTICAL,
            false
        )
        listBinding.recyclerView.adapter = PokemonListAdapter()
        val pokeList = pokeModel.pokeListObserver.value
        pokeList?.let {
            (listBinding.recyclerView.adapter as PokemonListAdapter).updateItems(pokeList.result)
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

    companion object {

        private val FIRST_GEN_POKE = 151

        private val COLUMNS_NUMBER = 2
    }
}