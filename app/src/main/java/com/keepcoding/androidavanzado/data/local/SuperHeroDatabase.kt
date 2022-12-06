package com.keepcoding.androidavanzado.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.keepcoding.androidavanzado.data.local.model.SuperHeroLocal
import com.keepcoding.androidavanzado.domain.SuperHero

@Database(entities = [SuperHeroLocal::class], version = 1)
abstract class SuperHeroDatabase : RoomDatabase() {
    abstract fun getDAO(): SuperHeroDAO
}
