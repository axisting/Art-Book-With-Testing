package com.axisting.artbookwithtesting.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.axisting.artbookwithtesting.model.Art


@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art : Art)

    @Delete
    suspend fun deleteArts(art : Art)



    @Query("SELECT *  FROM arts")
    fun observeArts() : LiveData<List<Art>>

}