package com.example.rparcas.pokedex.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class para la lista de pokemon
 * results [[PokemonResult],...]
 */
data class PokemonListApiResults(
    @SerializedName("results") val results: List<PokemonResult>
)

/**
 * Results de la respuesta anterior
 * {name:String, url:String}
 */
data class PokemonResult(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

/**
 * Datos que se obtienen cuando se pide por http la url del [PokemonResult]
 */
data class PokemonModel(
    @SerializedName("id") val id: Int,
    @SerializedName("sprites") val sprites: PokemonSprites,
    @SerializedName("name") val name: String,
    @SerializedName("order") val order: String,
    @SerializedName("types") val types: List<PokemonTypes>,
    @SerializedName("base_experience") val base_experience: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("stats") val stats: List<PokemonStatApi>
)

data class PokemonSprites(
    @SerializedName("front_default") val frontDefault: String,
    @SerializedName("back_default") val backDefault: String,
    @SerializedName("other") val otherSprites: OtherSprites
)
data class OtherSprites(
    @SerializedName("official-artwork") val officialArtWork: OfficialArtwork?
)
data class OfficialArtwork(
    @SerializedName("front_default") val frontDefault: String
)


data class PokemonTypes(
    @SerializedName("type") val type: PokemonType
)
data class PokemonType(
    @SerializedName("name") val name: String
)

data class PokemonStatApi(
    @SerializedName("base_stat") val value: Int,
    @SerializedName("stat") val pokemonStat: PokemonStatApiName
)

data class PokemonStatApiName(
    @SerializedName("name") val name: String
)