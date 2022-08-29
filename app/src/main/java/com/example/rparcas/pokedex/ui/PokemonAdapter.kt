package com.example.rparcas.pokedex.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.rparcas.pokedex.R
import com.example.rparcas.pokedex.domain.PokemonDomain

class PokemonAdapter (private val listPokemon:List<PokemonDomain>,
                      private val mItemListener:ItemClickListener,
                      private val mFavCheckListener:FavCheckBoxListener): RecyclerView.Adapter<PokemonViewHolder>() {

    var numOfColumnGrid = 1



    interface ItemClickListener{
        fun onItemClick(id:Int, view: View)
    }

    interface FavCheckBoxListener{
        fun onItemClick(isFav:Boolean,pokemon:PokemonDomain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.pokemon_item,parent,false))
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {



        val favCheckBoxListener = { _:View ->
            this.mFavCheckListener.onItemClick(
                holder.itemView.findViewById<CheckBox>(R.id.chekBoxFavPokemon).isChecked,
                listPokemon[position]
            )
        }
        val clickItemListener = { _:View ->
            this.mItemListener.onItemClick(listPokemon[position].id,holder.itemView)
        }

        holder.bind(listPokemon[position],numOfColumnGrid,clickItemListener, favCheckBoxListener)
        //holder.itemView.setOnClickListener
    }

    override fun getItemCount(): Int {
        return listPokemon.size
    }

    fun getPositionByIdPokemon(idPokemon: Int):Int{
        val pokemon = listPokemon.findLast { it.id == idPokemon }
        Log.d("PRUEBA","adapter: $pokemon")
        return listPokemon.indexOf(pokemon)
    }
}