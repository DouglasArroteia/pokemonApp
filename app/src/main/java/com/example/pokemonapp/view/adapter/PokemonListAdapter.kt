package com.example.pokemonapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.api.response.PokemonListResponseItem
import com.example.pokemonapp.extensions.imageURL
import com.example.pokemonapp.extensions.pokemonId
import com.example.pokemonapp.extensions.toPokemonNumber
import com.example.pokemonapp.persistence.SharedPreferencesHelper
import kotlinx.android.synthetic.main.pokemon_item_list.view.*
import java.util.*


/**
 * The RecyclerAdapter for the Pokemon list.
 */
class PokemonListAdapter(private val navigateToDetailed: (id: Int) -> Unit) :
    RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    private val items = mutableListOf<PokemonListResponseItem>()

    private lateinit var sharedPrefs: SharedPreferencesHelper


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_item_list, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() = with(itemView) {
            val position = adapterPosition
            val item = items[position]

            pokemon_name.text = item.name.capitalize(Locale.getDefault())
            val pokemonId = item.url.pokemonId()
            Glide.with(context)
                .asBitmap()
                .load(pokemonId.imageURL())
                .into(pokemon_icon)

            pokemon_number.text = pokemonId.toPokemonNumber(context)

            sharedPrefs = SharedPreferencesHelper(context)
            favorite_pokemon.background =
                if (sharedPrefs.isFavorite(item.name)) {
                    ContextCompat.getDrawable(context, R.drawable.favorite_selected)
                } else {
                    ContextCompat.getDrawable(context, R.drawable.favorite_unselected)
                }

            pokemon_card.setOnClickListener {
                navigateToDetailed.invoke(pokemonId.toInt())
            }
        }
    }

    /**
     * Update the adapter
     */
    fun updateAdapter(newItems: MutableList<PokemonListResponseItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
