package com.axisting.artbookwithtesting.repo

import androidx.lifecycle.LiveData
import com.axisting.artbookwithtesting.model.Art
import com.axisting.artbookwithtesting.model.ImageResponse
import com.axisting.artbookwithtesting.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt (art : Art)

    suspend fun deleteArt(art : Art)

    fun getArt () : LiveData<List<Art>>

    suspend fun searchImage (imageString: String) :Resource<ImageResponse>

}