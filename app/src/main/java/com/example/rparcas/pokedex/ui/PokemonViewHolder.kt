package com.example.rparcas.pokedex.ui

import android.opengl.Visibility
import android.util.Log
import android.view.View
import androidx.compose.ui.text.capitalize
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.rparcas.pokedex.Utils
import com.example.rparcas.pokedex.databinding.PokemonItemBinding
import com.example.rparcas.pokedex.domain.PokemonDomain
import com.squareup.picasso.Picasso
import java.util.*

class PokemonViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private val binding = PokemonItemBinding.bind(view)

    fun bind(pokemon:PokemonDomain){

        // color de la tarjeta
        binding.cvPokemon.setCardBackgroundColor(getColor(binding.root.context,Utils.obtenerReferenciaColorSegunTipo(pokemon.tipos[0].type.name)))

        binding.tvNombrePokemon.text = Utils.capitalize(pokemon.name)
        binding.tvTipo1.text = Utils.capitalize(pokemon.tipos[0].type.name).trim()
         if(pokemon.tipos.size > 1 ){
             binding.tvTipo2.visibility = View.VISIBLE
             binding.tvTipo2.text =  Utils.capitalize(pokemon.tipos[1].type.name).trim()
        }else{
             binding.tvTipo2.visibility = View.GONE
        }

        Picasso.get().load(pokemon.officialArtwork ?: pokemon.sprites[0]).into(binding.ivPokemon)
    }
}