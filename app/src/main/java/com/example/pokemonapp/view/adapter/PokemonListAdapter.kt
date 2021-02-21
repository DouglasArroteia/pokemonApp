package com.example.pokemonapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.api.response.models.PokemonListModelItem
import com.example.pokemonapp.databinding.PokemonItemListBinding
import com.example.pokemonapp.extensions.imageURL
import com.example.pokemonapp.extensions.pokemonId
import com.example.pokemonapp.extensions.toPokemonNumber
import com.example.pokemonapp.persistence.SharedPreferencesHelper
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
            PokemonItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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
            val context = itemListBinding.root.context
            itemListBinding.pokemonName.text = item.name.capitalize(Locale.getDefault())
            val pokemonId = item.url.pokemonId()
            com.bumptech.glide.Glide.with(context)
                .load(pokemonId.imageURL())
                .listener(
                    com.github.florent37.glidepalette.GlidePalette.with(pokemonId.imageURL())
                        .use(com.github.florent37.glidepalette.BitmapPalette.Profile.MUTED_LIGHT)
                        .intoCallBack { palette ->
                            val rgb = palette?.dominantSwatch?.rgb
                            if (rgb != null) {
                                itemListBinding.pokemonCard.setCardBackgroundColor(rgb)
                            }
                        }.crossfade(true)
                )
                .into(itemListBinding.pokemonIcon)

            itemListBinding.pokemonNumber.text = pokemonId.toPokemonNumber(context)

            val sharedPrefs = SharedPreferencesHelper(context)
            itemListBinding.favoritePokemon.background =
                if (sharedPrefs.isFavorite(item.name)) {
                    androidx.core.content.ContextCompat.getDrawable(
                        context,
                        com.example.pokemonapp.R.drawable.favorite_selected
                    )
                } else {
                    androidx.core.content.ContextCompat.getDrawable(
                        context,
                        com.example.pokemonapp.R.drawable.favorite_unselected
                    )
                }

            itemListBinding.pokemonCard.setOnClickListener {
                navigateToDetailed.invoke(pokemonId.toInt())
            }
        }
    }

    /**
     * Update the adapter
     */
    fun updateAdapter(newItems: MutableList<PokemonListModelItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
