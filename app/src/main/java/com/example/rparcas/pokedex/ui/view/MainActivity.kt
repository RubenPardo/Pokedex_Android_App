package com.example.rparcas.pokedex.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.example.rparcas.pokedex.databinding.ActivityMainBinding
import com.example.rparcas.pokedex.domain.PokemonDomain
import com.example.rparcas.pokedex.ui.BottomSheetFragmentTipoPokemon
import com.example.rparcas.pokedex.ui.PokemonAdapter
import com.example.rparcas.pokedex.ui.viewmodel.PokemonListViewModel

import android.content.Intent
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rparcas.pokedex.Utils
import androidx.core.app.ActivityOptionsCompat
import com.example.rparcas.pokedex.R


//https://pokeapi.co
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private val pokemonListViewModel: PokemonListViewModel by viewModels()

    private val mutableListPokemon:MutableList<PokemonDomain> = mutableListOf()
    private lateinit var adapter: PokemonAdapter

    companion object {
        const val KEY_POKEMON_ID = "key_id_pokemon"
        const val TAG_FILTRO_1 = "filtrar_tipo_1"
        const val TAG_FILTRO_2 = "filtrar_tipo_2"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initRecyclerView()

        binding.toggleListGridListaPokemon.setOnClickListener{
            val numColumns:Int = if(it.isActivated){
                // modo lista
                1
            }else{
                // modo grid
                2
            }
            it.isActivated = !it.isActivated
            adapter.numOfColumnGrid = numColumns
            binding.rvPokemon.layoutManager = GridLayoutManager(this,numColumns)
        }

        pokemonListViewModel.getListPokemon(1154)

        pokemonListViewModel.listPokemonLiveData.observe(this,{


            mutableListPokemon.clear() // no debemos borrar porque en teoria solo se entra una vez y no se pide
            // mas de una vez por lo que no habra duplicidad
            mutableListPokemon.addAll(it)

            mutableListPokemon.sortBy { e -> e.id }
            adapter.notifyDataSetChanged()

            if(it.isEmpty()){
                binding.rvPokemon.visibility = View.GONE
                binding.tvEmptyPokemonList.visibility = View.VISIBLE
            }else{
                binding.rvPokemon.visibility = View.VISIBLE
                binding.tvEmptyPokemonList.visibility = View.GONE
            }

        })

        // callback del boton que abre los filtros de tipo 1
        binding.chipButtonFiltroTipoPokemon1.setOnClickListener{
            mostrarDialogFiltroTipos(TAG_FILTRO_1)
        }
        // callback del boton que abre los filtros de tipo 2
        binding.chipButtonFiltroTipoPokemon2.setOnClickListener{
            mostrarDialogFiltroTipos(TAG_FILTRO_2)
        }

        binding.svNombrePokemon.setOnQueryTextListener(this)

        pokemonListViewModel.tipoFiltro1.observe(this,{
            binding.chipButtonFiltroTipoPokemon1.setChipBackgroundColorResource(
                Utils.obtenerReferenciaColorSegunTipo(
                    it ?: ""
                )
            )
            binding.chipButtonFiltroTipoPokemon1.text = Utils.capitalize(it ?: "TIPO 1")
            binding.chipButtonFiltroTipoPokemon1.tag = it
        })

        pokemonListViewModel.tipoFiltro2.observe(this,{
            binding.chipButtonFiltroTipoPokemon2.setChipBackgroundColorResource(
                Utils.obtenerReferenciaColorSegunTipo(
                    it ?: ""
                )
            )
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

        adapter = PokemonAdapter(mutableListPokemon,
            // callback del item click del adapter
            object : PokemonAdapter.ItemClickListener {
                override fun onItemClick(idPokemon:Int, view:View){
                    navegarAPokemonInfoActivity(idPokemon,view)
                }
            }
        )
        adapter.numOfColumnGrid = 1
        binding.rvPokemon.layoutManager = LinearLayoutManager(this)
        binding.rvPokemon.adapter = adapter
    }


    /**
     * Abrir la activity {PokemonInfoActivity.kt}
     * @param id del pokemon a mostrar en la siguiente pantalla
     */
    fun navegarAPokemonInfoActivity(id: Int, view: View){
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            view.findViewById(R.id.ivPokemonItem), "pokemonImagen"
        )
        val intent:Intent = Intent(this, PokemonInfoActivity::class.java)
        intent.putExtra(KEY_POKEMON_ID,id)
        startActivity(intent,options.toBundle())

    }


    // callbacks del search view para filtrar por nombre
    override fun onQueryTextSubmit(texto: String?): Boolean {
        pokemonListViewModel.filtrarPorNombre(texto)
        return false
    }

    override fun onQueryTextChange(texto: String?): Boolean {
        pokemonListViewModel.filtrarPorNombre(texto)
        return false
    }


}