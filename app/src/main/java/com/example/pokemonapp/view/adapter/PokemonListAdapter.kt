package com.example.pokemonapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.api.response.PokemonListResponseItem
import com.example.pokemonapp.extensions.imageURL
import com.example.pokemonapp.extensions.pokemonId
import com.example.pokemonapp.extensions.toPokemonNumber
import com.example.pokemonapp.view.DetailedPokemonFragment
import com.example.pokemonapp.view.model.PokeModel
import kotlinx.android.synthetic.main.pokemon_item_list.view.*
import java.util.*


class PokemonListAdapter : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    private val items = mutableListOf<PokemonListResponseItem>()

    private var pokeSelectedListener: PokemonSelectedListener? = null

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


            pokemon_card.setOnClickListener {
                pokeSelectedListener?.onPokemonSelected(pokemonId)
                val activity = context as AppCompatActivity
                val myFragment: Fragment = DetailedPokemonFragment()
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, myFragment)
                    .addToBackStack("detailed_fragment").commit()
            }
        }
    }

    fun updateItems(newItems: MutableList<PokemonListResponseItem>) {
        items.apply {
            items.clear()
            items.addAll(newItems)
        }
        notifyDataSetChanged()
    }

    fun registerPokemonSelectedListener(listener: PokemonSelectedListener) {
        pokeSelectedListener = listener
    }

    fun unregisterPokemonSelectedListener() {
        pokeSelectedListener = null
    }

    interface PokemonSelectedListener {
        fun onPokemonSelected(id: String)
    }
}
