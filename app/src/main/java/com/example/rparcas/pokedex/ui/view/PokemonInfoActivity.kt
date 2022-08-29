package com.example.rparcas.pokedex.ui.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.rparcas.pokedex.Utils
import com.example.rparcas.pokedex.databinding.ActivityPokemonInfoBinding
import com.example.rparcas.pokedex.domain.PokemonDomain
import com.example.rparcas.pokedex.ui.viewmodel.PokemonInfoViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonInfoBinding
    private val pokemonInfoViewModel: PokemonInfoViewModel by viewModels()

    /**
     * En el on create recibe el id del pokemon a mostrar,
     * con ese id se lo pasamos al view model y ya obtendra sus datos
     * de la BD
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idPokemon = intent.extras?.getInt(MainActivity.KEY_POKEMON_ID)
        if(idPokemon!=null){
            pokemonInfoViewModel.onCreate(idPokemon)
            pokemonInfoViewModel.pokemonLiveData.observe(this,
                {pokemon ->
                    bindInfoGeneralPokemon(pokemon)
                    bindStatsPokemonData(pokemon)
                })


        }
    }

    private fun bindStatsPokemonData(pokemon: PokemonDomain) {

        val color = ContextCompat.getColor(binding.root.context, Utils.obtenerReferenciaColorSegunTipo(pokemon.tipos[0].type.name))

        binding.tvPeso.text = "${pokemon.weight} lb"
        binding.tvAltura.text = "${pokemon.height} ft"
        binding.tvBaseExperience.text = pokemon.base_experience.toString()


        setViewPokemonStat(binding.tvHP,binding.tvTitleHP,pokemon.stats[0].value.toString(),binding.pBarHP,255,color)
        setViewPokemonStat(binding.tvAttack,binding.tvAttackTitle,pokemon.stats[1].value.toString(),binding.pBarAttack,190,color)
        setViewPokemonStat(binding.tvDefense,binding.tvTitleDefense,pokemon.stats[2].value.toString(),binding.pBarDefense,230,color)
        setViewPokemonStat(binding.tvSpAttack,binding.tvTitleSpecialAttack,pokemon.stats[3].value.toString(),binding.pBarSpecialAttack,194,color)
        setViewPokemonStat(binding.tvSpDefense,binding.tvTitleSpecialDefense,pokemon.stats[4].value.toString(),binding.pBarSpecialDefense,230,color)
        setViewPokemonStat(binding.tvSpeed,binding.tvTitleSpeed,pokemon.stats[5].value.toString(),binding.pBarSpeed,180,color)



    }

    /**
     * poner los valores de los estados en la vista en el text view y los progress bar,
     * cambia el color de las barras y los titulos
     */
    private fun setViewPokemonStat(tvValorStat: TextView, tvStatTitle: TextView, value: String,pBar:ProgressBar, maxValueStat: Int,color: Int) {
        tvValorStat.text = value
        tvStatTitle.setTextColor(color)
        pBar.max = maxValueStat
        pBar.progress = value.toInt()
        pBar.progressTintList = ColorStateList.valueOf(color);
    }

    /**
     * Inicializar las vistas de ifno del pokemon
     */
    private fun bindInfoGeneralPokemon(pokemon: PokemonDomain) {

        binding.tvInfoTipo1.text = Utils.capitalize(pokemon.tipos[0].type.name)
        if(pokemon.tipos.size > 1 ){
            binding.tvInfoTipo1.visibility = View.VISIBLE
            binding.tvInfoTipo2.text =  Utils.capitalize(pokemon.tipos[1].type.name).trim()
        }else{
            binding.tvInfoTipo2.visibility = View.GONE
        }

        if(pokemon.order == "-1"){
            binding.tvPokemonInfoOrden.visibility = View.GONE
        }else{
            binding.tvPokemonInfoOrden.text = Utils.formatearOrdenPokemon(pokemon.order.toInt())
        }
        binding.tvPokemonInfoNombre.text = Utils.capitalize(pokemon.name)

        binding.layoutBackground.setBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                Utils.obtenerReferenciaColorSegunTipo(pokemon.tipos[0].type.name)
            )
        )
        Picasso.get().load(pokemon.officialArtwork ?: pokemon.sprites[0]).into(binding.ivPokemonInfo)
    }
}