package com.example.rparcas.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.rparcas.pokedex.data.PokemonRepository
import dagger.hilt.android.AndroidEntryPoint
import com.example.rparcas.pokedex.databinding.ActivityMainBinding
import com.example.rparcas.pokedex.domain.PokemonDomain
import com.example.rparcas.pokedex.ui.PokemonListViewModel

//https://pokeapi.co/docs/v2#pokemon-section
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pokemonListViewModel: PokemonListViewModel by viewModels()

    private val mutableListPokemon:MutableList<PokemonDomain> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPrueba.setOnClickListener {
            pokemonListViewModel.getListPokemon(1154)
        }

        pokemonListViewModel.listPokemon.observe(this,{
            Log.d("PRUEBA","LLEGA LISTA -----------------------------")
            mutableListPokemon.addAll(it)

            mutableListPokemon.sortBy { e -> e.id }

            Log.d("PRUEBA","${mutableListPokemon.map { e -> e.id }}")
        })
    }
}