package com.joaquinco.androidavanzado.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joaquinco.androidavanzado.data.local.model.SuperHeroLocal

@Database(entities = [SuperHeroLocal::class], version = 1)
abstract class SuperHeroDatabase : RoomDatabase() {
    abstract fun getDAO(): SuperHeroDAO
}
