package com.example.rparcas.pokedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.rparcas.pokedex.ui.view.PokemonListActivity
import com.example.rparcas.pokedex.R
import com.example.rparcas.pokedex.Utils
import com.example.rparcas.pokedex.databinding.BottomSheetFiltroTiposLayoutBinding
import com.example.rparcas.pokedex.ui.viewmodel.PokemonListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

/**
 * A este fragment se le puede invocar de dos botones, el del tipo de pokemon 1 o 2,
 * para difernciarlo se hace mediante el tag (this.tag) que puede ser o "tipo_filtrar_1" o 2
 * Asi cuando en este fragment se seleccione un tipo se le pasa al 1 o al 2 del view model
 */
class BottomSheetFragmentTipoPokemon() : BottomSheetDialogFragment(),View.OnClickListener {

    private lateinit var binding:  BottomSheetFiltroTiposLayoutBinding
    //private val pokemonListViewModel: PokemonListViewModel by viewModels()  de esta forma no se puede hacer
    private lateinit var pokemonListViewModel: PokemonListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = BottomSheetFiltroTiposLayoutBinding.inflate(inflater,container,false)
        // asi se accede al view model desde un fragment
        val activity = requireActivity()
        pokemonListViewModel = ViewModelProvider(activity)[PokemonListViewModel::class.java]

        initGridLayoutTipos()

        return binding.root
    }

    /**
     * Añade todos los chip buttons de los tipos pokemon, si se lanzo con el tipo 1,
     * ver cual tipo es y ponerle un icono de X al lado
     */
    private fun initGridLayoutTipos() {
        for (tipo in Utils.LISTA_TIPO_POKEMON){


            // crear chip button
            val chipButton = crearChipButtonTipoPokemon(tipo)
            binding.chipTipoPokemonFiltroGridLayout.addView(chipButton)
        }
    }

    /**
     * Crear un chip button de tipo de pokemon con su respectivo color
     * y nombre segun el tipo pasado
     *
     * si esta marcado (tipo == tipoFiltro(1 o 2).value) se le pone un X indicativa
     */
    private fun crearChipButtonTipoPokemon(tipo: String): View? {
        val chipButton = layoutInflater.inflate(R.layout.chip_button_tipo_pokemon_layout, null, false) as Chip
        chipButton.setChipBackgroundColorResource(Utils.obtenerReferenciaColorSegunTipo(tipo))
        chipButton.text = Utils.capitalize(tipo)
        chipButton.tag = tipo
        chipButton.setOnClickListener(this)


        if (this.tag == PokemonListActivity.TAG_FILTRO_1 && tipo == pokemonListViewModel.tipoFiltro1.value){
            // si hay un tipo seleccionado poner una X
            chipButton.isCloseIconVisible = true
        }
        if (this.tag == PokemonListActivity.TAG_FILTRO_2 && tipo == pokemonListViewModel.tipoFiltro2.value){
            // si hay un tipo seleccionado poner una X
            chipButton.isCloseIconVisible = true

        }
        return chipButton
    }

    override fun onClick(p0: View?) {

        if(this.tag == PokemonListActivity.TAG_FILTRO_1){
            pokemonListViewModel.filtrarTipo1(p0?.tag.toString())
        }else if(this.tag == PokemonListActivity.TAG_FILTRO_2){
            pokemonListViewModel.filtrarTipo2(p0?.tag.toString())
        }
        dismiss()
    }


}

