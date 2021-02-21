package com.example.pokemonapp.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.api.response.models.PokemonDetailsModel
import com.example.pokemonapp.databinding.DetailedPokemonFragmentBinding
import com.example.pokemonapp.extensions.*
import com.example.pokemonapp.persistence.SharedPreferencesHelper
import com.example.pokemonapp.utils.ApplicationUtils
import com.example.pokemonapp.view.utils.ChartModel
import com.example.pokemonapp.view.observables.PokemonObservables
import com.example.pokemonapp.view.viewmodel.FavoriteViewModel
import com.example.pokemonapp.view.viewmodel.PokemonViewModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment that handle the details of a pokemon.
 */
class DetailedPokemonFragment : BasePokemonFragment() {

    private val pokemonViewModel by viewModel<PokemonViewModel>()
    private val favoriteViewModel by viewModel<FavoriteViewModel>()

    private val pokemonObservables: PokemonObservables by inject()

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

        pokemonObservables.pokeLoadedObserver.observe(
            viewLifecycleOwner,
            { if (it) initComponents() })

        pokemonObservables.pokeIdObserver.value?.let {
            pokemonViewModel.getPokemon(it)
        }
    }

    /**
     * Initialize the components of the fragment
     */
    private fun initComponents() {
        val pokemonData = pokemonObservables.pokeDetailsObserver.value
        pokemonData?.let {
            initializeDetails(it)

            initializeFavoriteButton(it)
        }
    }

    /**
     * Initialize the details shown in the fragment
     */
    private fun initializeDetails(pokemonDetailsModel: PokemonDetailsModel) {
        val pokemonUrl = pokemonDetailsModel.id.toString().imageURL()
        Glide.with(requireContext())
            .load(pokemonUrl)
            .listener(
                GlidePalette.with(pokemonUrl)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack { palette ->
                        val rgb = palette?.dominantSwatch?.rgb
                        if (rgb != null) {
                            detailedPokeBinding.pokemonDetailsCard.setCardBackgroundColor(rgb)
                        }
                    }.crossfade(true)
            )
            .into(detailedPokeBinding.pokemonBigIcon)
        detailedPokeBinding.pokemonHeight.text =
            pokemonDetailsModel.height.toString().toPokemonHeight(requireContext())
        detailedPokeBinding.pokemonWeight.text =
            pokemonDetailsModel.weight.toString().toPokemonWeight(requireContext())
        val types = pokemonDetailsModel.types
        detailedPokeBinding.pokemonTypes1.text =
            types[0].type.name.toPokemonType()
        if (types.size > 1) {
            detailedPokeBinding.pokemonTypesString.text =
                context?.getString(R.string.pokemon_types)
            detailedPokeBinding.pokemonTypes2.visibility = View.VISIBLE
            detailedPokeBinding.pokemonTypes2.text = types[1].type.name.toPokemonType()
        }
        loadStatusChart(pokemonDetailsModel)
    }

    /**
     * Loads the graph chart to show the pokemon stats
     */
    private fun loadStatusChart(pokemonDetails: PokemonDetailsModel) {
        context?.let {
            val chartModel: AAChartModel = ChartModel().getChartModel()
                .subtitle(pokemonDetails.name.toPokemonStats(it))
                .colorsTheme(ApplicationUtils(it).statsColor)
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

    /**
     * Load the info for the favorite button
     */
    private fun initializeFavoriteButton(pokemonDetailsModel: PokemonDetailsModel) {
        context?.let { ctx ->
            val pokeName = pokemonDetailsModel.name
            detailedPokeBinding.favoritePokemon.background =
                if (sharedPrefs.isFavorite(pokeName)) {
                    ContextCompat.getDrawable(ctx, R.drawable.favorite_selected)
                } else {
                    ContextCompat.getDrawable(ctx, R.drawable.favorite_unselected)
                }
            detailedPokeBinding.favoritePokemon.setOnClickListener {
                handleFavoriteButton(ctx, pokeName)
            }
        }
    }

    /**
     * Handles the faovite button click
     */
    private fun handleFavoriteButton(ctx: Context, pokeName: String) {
        detailedPokeBinding.favoritePokemon.background =
            if (sharedPrefs.isFavorite(pokeName)) {
                sharedPrefs.unFavorite(pokeName)
                ContextCompat.getDrawable(ctx, R.drawable.favorite_unselected)
            } else {
                sharedPrefs.favorite(pokeName)
                favoriteViewModel.setFavorite(pokeName)
                ContextCompat.getDrawable(ctx, R.drawable.favorite_selected)
            }
    }
}