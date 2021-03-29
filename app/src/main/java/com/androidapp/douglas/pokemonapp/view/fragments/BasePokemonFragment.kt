package com.androidapp.douglas.pokemonapp.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.androidapp.douglas.pokemonapp.loader.PokemonLoader
import com.androidapp.douglas.pokemonapp.R
import com.androidapp.douglas.pokemonapp.view.observables.PokemonObservables
import com.androidapp.douglas.pokemonapp.view.viewutils.getDialog
import org.koin.android.ext.android.inject

abstract class BasePokemonFragment : Fragment() {

    private val pokemonObservables: PokemonObservables by inject()

    private val loadingDialog: Dialog by lazy {
        getDialog(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemonObservables.pokeLoaderObserver.observe(
            viewLifecycleOwner,
            { handleState() })
    }

    /**
     * Handles the loading dialog state
     */
    private fun handleState() {
        when (val state = pokemonObservables.pokeLoaderObserver.value) {
            is PokemonLoader.Loading -> {
                if (state.isLoading) {
                    loadingDialog.show()
                } else {
                    loadingDialog.dismiss()
                }
            }
            is PokemonLoader.DefaultError -> {
                Toast.makeText(
                    context,
                    getString(R.string.default_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}