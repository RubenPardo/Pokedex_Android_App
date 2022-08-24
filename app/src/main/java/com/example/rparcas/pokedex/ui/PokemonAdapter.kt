package com.example.rparcas.pokedex.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rparcas.pokedex.R
import com.example.rparcas.pokedex.domain.PokemonDomain

class PokemonAdapter (private val listPokemon:List<PokemonDomain>): RecyclerView.Adapter<PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.pokemon_item,parent,false))
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {

        holder.bind(listPokemon[position])
    }

    override fun getItemCount(): Int {
        return listPokemon.size
    }
}