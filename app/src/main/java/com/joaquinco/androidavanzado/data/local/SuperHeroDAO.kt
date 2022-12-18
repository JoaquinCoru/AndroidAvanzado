package com.joaquinco.androidavanzado.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.joaquinco.androidavanzado.data.local.model.SuperHeroLocal

@Dao
interface SuperHeroDAO {
    @Query("SELECT * FROM superheros")
    fun getAllSuperheros(): List<SuperHeroLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(superHero: List<SuperHeroLocal>)

    @Update
    fun updateSuperHero(superHero: SuperHeroLocal)
}
