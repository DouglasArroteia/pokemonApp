package com.example.pokemonapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.DetailedPokemonFragmentBinding
import com.example.pokemonapp.extensions.*
import com.example.pokemonapp.loader.PokemonLoader
import com.example.pokemonapp.view.model.PokeModel
import com.example.pokemonapp.view.model.PokemonViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailedPokemonFragment : Fragment() {

    private val pokemonViewModel by sharedViewModel<PokemonViewModel>()
    private val pokeModel: PokeModel by inject()

    private lateinit var detailedPokeBinding: DetailedPokemonFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailedPokeBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.detailed_pokemon_fragment, container,
                false
            )
        return detailedPokeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokeModel.pokeLoaderObserver.observe(
            viewLifecycleOwner,
            Observer { handleState(it) })
        pokeModel.pokeLoadedObserver.observe(
            viewLifecycleOwner,
            Observer { if (it) initComponents() })

        pokeModel.pokeIdObserver.value?.let {
            pokemonViewModel.getPokemon(it)
        }
    }

    private fun initComponents() {
        pokeModel.pokeDetailsObserver.value?.let {
            initializeDetails()
        }
    }

    private fun initializeDetails() {
        val data = pokeModel.pokeDetailsObserver.value
        data?.let {
            Glide.with(requireContext())
                .asBitmap()
                .load(data.id.toString().imageURL())
                .into(detailedPokeBinding.pokemonBigIcon)
            detailedPokeBinding.pokemonHeight.text =
                it.height.toString().toPokemonHeight(requireContext())
            detailedPokeBinding.pokemonWeight.text =
                it.weight.toString().toPokemonWeight(requireContext())
            val types = it.types
            detailedPokeBinding.pokemonTypes1.text =
                types[0].type.name.toPokemonType()
            if (types.size > 1) {
                detailedPokeBinding.pokemonTypesString.text =
                    context?.getString(R.string.pokemon_types)
                detailedPokeBinding.pokemonTypes2.visibility = View.VISIBLE
                detailedPokeBinding.pokemonTypes2.text = types[1].type.name.toPokemonType()
            }
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
}