package com.example.pokemonapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.api.Urls
import com.example.pokemonapp.api.response.PokemonListResponseItem
import com.example.pokemonapp.extensions.imageURL
import com.example.pokemonapp.extensions.toPokemonNumber
import kotlinx.android.synthetic.main.pokemon_item_list.view.*
import java.text.Normalizer
import java.util.*
import java.util.logging.Logger

class PokemonListAdapter : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    private val items = mutableListOf<PokemonListResponseItem>()

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
            val pokemonId = item.url.substringAfter("pokemon/").replace("/", "")
            Glide.with(context)
                .asBitmap()
                .load(pokemonId.imageURL())
                .into(pokemon_icon)

            pokemon_number.text = pokemonId.toPokemonNumber(context)


            pokemon_card.setOnClickListener {
                //TODO
            }
        }
    }

    private fun String.normalizeString(): String {
        return Normalizer.normalize(this, Normalizer.Form.NFD).replace(Regex("[^\\p{ASCII}]"), "")
            .toLowerCase(
                Locale.getDefault()
            )
    }

    fun updateItems(newItems: MutableList<PokemonListResponseItem>) {
        items.apply {
            items.clear()
            items.addAll(newItems)
        }
        notifyDataSetChanged()
    }
}
