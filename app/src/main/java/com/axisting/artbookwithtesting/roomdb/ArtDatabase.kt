package com.axisting.artbookwithtesting.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.axisting.artbookwithtesting.model.Art

@Database(entities = arrayOf(Art::class) , version = 1)
abstract class ArtDatabase : RoomDatabase() {

    abstract fun artDao () : ArtDao

}