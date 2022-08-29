package com.example.rparcas.pokedex.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rparcas.pokedex.data.model.PokemonStatApi
import com.example.rparcas.pokedex.domain.PokemonDomain
import com.example.rparcas.pokedex.domain.PokemonStat
import com.google.gson.Gson

@Entity(tableName = "pokemon_table")
data class PokemonEntity (
    @PrimaryKey() @ColumnInfo(name = "id") val id:Int=0,
    @ColumnInfo(name = "order") val order:String,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "officialArtwork") val officialArtwork:String?,
    @ColumnInfo(name = "sprites") val sprites:String,
    @ColumnInfo(name = "tipos") val tipos:String,
    @ColumnInfo(name ="base_experience") val base_experience: Int,
    @ColumnInfo(name ="height") val height: Int,
    @ColumnInfo(name ="weight") val weight: Int,
    @ColumnInfo(name ="stats") val stats: String?
)



fun PokemonDomain.toDatabase() =
    PokemonEntity(
        id=id,
        order = order,
        name = name,
        officialArtwork = officialArtwork,
        sprites = Gson().toJson(sprites),
        tipos = Gson().toJson(tipos),
        base_experience = base_experience,
        height = height,
        weight = weight,
        stats = Gson().toJson(stats)

    )