package com.example.rparcas.pokedex

import android.graphics.Color
import android.util.Log
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.capitalize
import com.example.rparcas.pokedex.data.model.PokemonTypes
import java.util.*

class Utils {

    companion object{
        fun capitalize(texto:String):String{
            return texto.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        }

        fun obtenerReferenciaColorSegunTipo(tipo: String): Int {
            var color:Int  = R.color.cardview_shadow_end_color

            when(tipo){
                "grass" -> color =  R.color.planta
                "fire" -> color = R.color.fuego
                "normal" -> color = R.color.normal
                "water" -> color = R.color.agua
                "electric" -> color = R.color.electrico
                "ice" -> color = R.color.hielo
                "fighting" -> color = R.color.lucha
                "poison" -> color = R.color.veneno
                "ground" -> color = R.color.tierra
                "flying" -> color = R.color.volador
                "psychic" -> color = R.color.psicquico
                "bug" -> color = R.color.bicho
                "rock" -> color = R.color.roca
                "ghost" -> color = R.color.fantasma
                "dragon" -> color = R.color.dragon
                "dark" -> color = R.color.siniestro
                "steel" -> color = R.color.acero
                "fairy" -> color = R.color.hada
                "" -> color = R.color.chipBackgroundDefault

            }
            return color
        }

        /**
         * Formatear el numero de orden a #XXX
         * @param order string con un numero con formato 1-9999
         * @return string con formato #XXX
         */
        fun formatearOrdenPokemon(order: Int): String {
            var resultado = ""
            if(order<10){
                resultado = "#00$order"
            }else if(order<100){
                resultado = "#0$order"
            }else{
                resultado = "#$order"
            }



            return resultado
        }


        val LISTA_TIPO_POKEMON = listOf<String>(
            "grass",
            "fire",
            "normal",
            "water",
            "electric",
            "ice",
            "fighting",
            "poison",
            "ground",
            "flying",
            "psychic",
            "bug",
            "rock",
            "ghost",
            "dragon",
            "dark",
            "steel",
            "fairy"
        )

    }
}