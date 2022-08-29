package com.example.rparcas.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rparcas.pokedex.data.database.entities.PokemonEntity
import com.example.rparcas.pokedex.data.model.PokemonListApiResults
import com.example.rparcas.pokedex.domain.PokemonDomain

@Dao
interface PokemonDao {

    @Query("select * from pokemon_table")
    suspend fun getAllPokemon(): List<PokemonEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList:List<PokemonEntity>)

    @Query("select * from pokemon_table where id=:id")
    suspend fun getPokemonById(id: Int): PokemonEntity

}

