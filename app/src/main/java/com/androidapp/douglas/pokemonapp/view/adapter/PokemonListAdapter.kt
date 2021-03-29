package com.androidapp.douglas.pokemonapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.douglas.pokemonapp.R
import com.androidapp.douglas.pokemonapp.api.response.models.PokemonListModelItem
import com.androidapp.douglas.pokemonapp.extensions.imageURL
import com.androidapp.douglas.pokemonapp.extensions.pokemonId
import com.androidapp.douglas.pokemonapp.extensions.toPokemonNumber
import com.androidapp.douglas.pokemonapp.persistence.SharedPreferencesHelper
import com.bumptech.glide.Glide
import com.androidapp.douglas.pokemonapp.databinding.PokemonItemListBinding
import com.github.florent37.glidepalette.GlidePalette
import java.util.*


/**
 * The RecyclerAdapter for the Pokemon list.
 */
class PokemonListAdapter(private val navigateToDetailed: (id: Int) -> Unit) :
    RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>() {

    private val items = mutableListOf<PokemonListModelItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonListViewHolder {
        val itemListBinding =
            PokemonItemListBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )

        return PokemonListViewHolder(itemListBinding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    inner class PokemonListViewHolder(private val itemListBinding: PokemonItemListBinding) :
        RecyclerView.ViewHolder(itemListBinding.root) {
        fun bind(item: PokemonListModelItem) {
            itemListBinding.apply {
                val context = root.context
                pokemonName.text = item.name.capitalize(Locale.getDefault())
                val pokemonId = item.url.pokemonId()
                Glide.with(context)
                    .load(pokemonId.imageURL())
                    .listener(
                        GlidePalette.with(pokemonId.imageURL())
                            .use(com.github.florent37.glidepalette.BitmapPalette.Profile.MUTED_LIGHT)
                            .intoCallBack { palette ->
                                val rgb = palette?.dominantSwatch?.rgb
                                if (rgb != null) {
                                    pokemonCard.setCardBackgroundColor(rgb)
                                }
                            }.crossfade(true)
                    )
                    .into(pokemonIcon)

                pokemonNumber.text = pokemonId.toPokemonNumber(context)

                val sharedPrefs = SharedPreferencesHelper(context)
                favoritePokemon.background =
                    if (sharedPrefs.isFavorite(item.name)) {
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.favorite_selected
                        )
                    } else {
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.favorite_unselected
                        )
                    }

                pokemonCard.setOnClickListener {
                    navigateToDetailed.invoke(pokemonId.toInt())
                }
            }
        }
    }

    /**
     * Update the adapter
     */
    fun updateAdapter(newItems: MutableList<PokemonListModelItem>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
