package com.example.pokemonapp.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.api.response.PokemonDetailsResponse
import com.example.pokemonapp.databinding.DetailedPokemonFragmentBinding
import com.example.pokemonapp.databinding.MainActivityBinding
import com.example.pokemonapp.extensions.*
import com.example.pokemonapp.loader.PokemonLoader
import com.example.pokemonapp.persistence.SharedPreferencesHelper
import com.example.pokemonapp.view.model.FavoriteViewModel
import com.example.pokemonapp.view.model.PokemonViewModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailedPokemonFragment : Fragment() {

    private val pokemonViewModel by sharedViewModel<PokemonViewModel>()
    private val favoriteViewModel by viewModel<FavoriteViewModel>()

    private lateinit var detailedPokeBinding: DetailedPokemonFragmentBinding

    private val sharedPrefs: SharedPreferencesHelper by inject()

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

        pokemonViewModel.pokeModel.pokeLoaderObserver.observe(
            viewLifecycleOwner,
            Observer {
                handleState(it)
            })
        pokemonViewModel.pokeModel.pokeLoadedObserver.observe(
            viewLifecycleOwner,
            Observer { if (it) initComponents() })

        pokemonViewModel.pokeModel.pokeIdObserver.value?.let {
            pokemonViewModel.getPokemon(it)
        }
    }

    private fun initComponents() {
        val pokemonData = pokemonViewModel.pokeModel.pokeDetailsObserver.value
        pokemonData?.let {
            initializeDetails(it)

            initializeFavoriteButton(it)
        }
    }

    private fun initializeDetails(pokemonDetailsResponse: PokemonDetailsResponse) {
        Glide.with(requireContext())
            .asBitmap()
            .load(pokemonDetailsResponse.id.toString().imageURL())
            .into(detailedPokeBinding.pokemonBigIcon)
        detailedPokeBinding.pokemonHeight.text =
            pokemonDetailsResponse.height.toString().toPokemonHeight(requireContext())
        detailedPokeBinding.pokemonWeight.text =
            pokemonDetailsResponse.weight.toString().toPokemonWeight(requireContext())
        val types = pokemonDetailsResponse.types
        detailedPokeBinding.pokemonTypes1.text =
            types[0].type.name.toPokemonType()
        if (types.size > 1) {
            detailedPokeBinding.pokemonTypesString.text =
                context?.getString(R.string.pokemon_types)
            detailedPokeBinding.pokemonTypes2.visibility = View.VISIBLE
            detailedPokeBinding.pokemonTypes2.text = types[1].type.name.toPokemonType()
        }
        loadStatusChart(pokemonDetailsResponse)
    }

    private fun loadStatusChart(pokemonDetails: PokemonDetailsResponse) {
        context?.let {
            val chartModel: AAChartModel = ChartModel().getChartModel()
                .title(pokemonDetails.name.toPokemonStats(it))
                .colorsTheme(pokemonViewModel.statsColor)
                .series(
                    arrayOf(
                        AASeriesElement()
                            .name(getString(R.string.pokemon_details_attack))
                            .data(arrayOf(pokemonDetails.stats[1].baseStat)),
                        AASeriesElement()
                            .name(getString(R.string.pokemon_details_s_attack))
                            .data(arrayOf(pokemonDetails.stats[3].baseStat)),
                        AASeriesElement()
                            .name(getString(R.string.pokemon_details_hp))
                            .data(arrayOf(pokemonDetails.stats[0].baseStat)),
                        AASeriesElement()
                            .name(getString(R.string.pokemon_details_def))
                            .data(arrayOf(pokemonDetails.stats[2].baseStat)),
                        AASeriesElement()
                            .name(getString(R.string.pokemon_details_s_def))
                            .data(arrayOf(pokemonDetails.stats[4].baseStat)),
                        AASeriesElement()
                            .name(getString(R.string.pokemon_details_speed))
                            .data(arrayOf(pokemonDetails.stats[5].baseStat)),
                    )
                )
            detailedPokeBinding.statusChart.aa_drawChartWithChartModel(chartModel)
        }
    }

    private fun initializeFavoriteButton(pokemonDetailsResponse: PokemonDetailsResponse) {
        context?.let { ctx ->
            val pokeName = pokemonDetailsResponse.name
            Log.d("DOUGLAS", "isFavorite: ${sharedPrefs.isFavorite(pokeName)} ")
            detailedPokeBinding.favoriteButton.background =
                if (sharedPrefs.isFavorite(pokeName)) {
                    ContextCompat.getDrawable(ctx, R.drawable.favorite_selected)
                } else {
                    ContextCompat.getDrawable(ctx, R.drawable.favorite_unselected)
                }
            detailedPokeBinding.favoriteButton.setOnClickListener {
                Log.d("DOUGLAS", "onClick: ${sharedPrefs.isFavorite(pokeName)} ")
                handleFavoriteButton(ctx, pokeName)
            }
        }
    }


    private fun handleFavoriteButton(ctx: Context, pokeName: String) {
        detailedPokeBinding.favoriteButton.background =
            if (sharedPrefs.isFavorite(pokeName)) {
                sharedPrefs.unFavorite(pokeName)
                ContextCompat.getDrawable(ctx, R.drawable.favorite_unselected)
            } else {
                sharedPrefs.favorite(pokeName)
                favoriteViewModel.setFavorite(pokeName)
                ContextCompat.getDrawable(ctx, R.drawable.favorite_selected)
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