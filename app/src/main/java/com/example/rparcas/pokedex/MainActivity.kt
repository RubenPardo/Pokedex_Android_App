package com.example.rparcas.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.example.rparcas.pokedex.databinding.ActivityMainBinding
import com.example.rparcas.pokedex.domain.PokemonDomain
import com.example.rparcas.pokedex.ui.BottomSheetFragmentTipoPokemon
import com.example.rparcas.pokedex.ui.PokemonAdapter
import com.example.rparcas.pokedex.ui.PokemonListViewModel

//https://pokeapi.co/docs/v2#pokemon-section
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pokemonListViewModel: PokemonListViewModel by viewModels()

    private val mutableListPokemon:MutableList<PokemonDomain> = mutableListOf()
    private lateinit var adapter: PokemonAdapter

    companion object {
        val TAG_FILTRO_1 = "filtrar_tipo_1"
        val TAG_FILTRO_2 = "filtrar_tipo_2"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initRecyclerView()
        pokemonListViewModel.getListPokemon(1154)

        pokemonListViewModel.listPokemonLiveData.observe(this,{
            mutableListPokemon.clear() // no debemos borrar porque en teoria solo se entra una vez y no se pide
            // mas de una vez por lo que no habra duplicidad
            mutableListPokemon.addAll(it)

            mutableListPokemon.sortBy { e -> e.id }
            adapter.notifyDataSetChanged()

        })

        // callback del boton que abre los filtros de tipo 1
        binding.chipButtonFiltroTipoPokemon1.setOnClickListener{
            mostrarDialogFiltroTipos(TAG_FILTRO_1)
        }
        // callback del boton que abre los filtros de tipo 2
        binding.chipButtonFiltroTipoPokemon2.setOnClickListener{
            mostrarDialogFiltroTipos(TAG_FILTRO_2)
        }

        pokemonListViewModel.tipoFiltro1.observe(this,{
            binding.chipButtonFiltroTipoPokemon1.setChipBackgroundColorResource(Utils.obtenerReferenciaColorSegunTipo(it?:""))
            binding.chipButtonFiltroTipoPokemon1.text = Utils.capitalize(it ?: "TIPO 1")
            binding.chipButtonFiltroTipoPokemon1.tag = it
        })

        pokemonListViewModel.tipoFiltro2.observe(this,{
            binding.chipButtonFiltroTipoPokemon2.setChipBackgroundColorResource(Utils.obtenerReferenciaColorSegunTipo(it?:""))
            binding.chipButtonFiltroTipoPokemon2.text = Utils.capitalize(it ?: "TIPO 2")
            binding.chipButtonFiltroTipoPokemon2.tag = it
        })
    }

    /**
     * Mostrar el dialogo de filtro de tipos
     */
    private fun mostrarDialogFiltroTipos(tag: String) {

        val bottomFragment = BottomSheetFragmentTipoPokemon()
        bottomFragment.show(supportFragmentManager,tag)

    }


    private fun initRecyclerView() {

        adapter = PokemonAdapter(mutableListPokemon)
        binding.rvPokemon.layoutManager = LinearLayoutManager(this)
        binding.rvPokemon.adapter = adapter
    }


}