package com.example.rparcas.pokedex.ui

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.rparcas.pokedex.Utils
import com.example.rparcas.pokedex.databinding.PokemonItemBinding
import com.example.rparcas.pokedex.domain.PokemonDomain
import com.squareup.picasso.Picasso

class PokemonViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private val binding = PokemonItemBinding.bind(view)

    fun bind(
        pokemon: PokemonDomain,
        numOfColumnGrid: Int,
        clickItemListener: (View) -> Unit,
        favCheckBoxListener: (View) -> Unit
    ){

        // callbacks
        this.itemView.setOnClickListener(clickItemListener)
        binding.chekBoxFavPokemon.setOnClickListener(favCheckBoxListener)

        // color de la tarjeta
        binding.cvPokemon.setCardBackgroundColor(getColor(binding.root.context,Utils.obtenerReferenciaColorSegunTipo(pokemon.tipos[0].type.name)))
        Picasso.get().load(pokemon.officialArtwork ?: pokemon.sprites[0]).into(binding.ivPokemonItem)
        binding.chekBoxFavPokemon.isChecked = pokemon.isFav


        if(numOfColumnGrid == 1){
            // modo linear
            binding.tvTipo1.visibility = View.VISIBLE
            binding.tvTipo2.visibility = View.VISIBLE
            binding.tvNombrePokemon.visibility = View.VISIBLE

            binding.tvNombrePokemon.text = Utils.capitalize(pokemon.name)
            binding.tvTipo1.text = Utils.capitalize(pokemon.tipos[0].type.name).trim()
            if(pokemon.tipos.size > 1 ){
                binding.tvTipo2.visibility = View.VISIBLE
                binding.tvTipo2.text =  Utils.capitalize(pokemon.tipos[1].type.name).trim()
            }else{
                binding.tvTipo2.visibility = View.GONE
            }

        }else{
            // modo grid
            binding.tvTipo1.visibility = View.GONE
            binding.tvTipo2.visibility = View.GONE
            binding.tvNombrePokemon.visibility = View.GONE
        }





    }
}