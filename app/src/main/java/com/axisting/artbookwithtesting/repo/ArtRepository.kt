package com.axisting.artbookwithtesting.repo

import androidx.lifecycle.LiveData
import com.axisting.artbookwithtesting.api.RetrofitAPI
import com.axisting.artbookwithtesting.model.Art
import com.axisting.artbookwithtesting.model.ImageResponse
import com.axisting.artbookwithtesting.roomdb.ArtDao
import com.axisting.artbookwithtesting.roomdb.ArtDatabase
import com.axisting.artbookwithtesting.util.Resource
import javax.inject.Inject


class ArtRepository
    @Inject
    constructor(

        private val artDao: ArtDao ,
        private val retrofitAPI: RetrofitAPI
    )  : ArtRepositoryInterface{
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArts(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("error" , null)
            } else {
                Resource.error("error" , null)
            }
        }catch (e : Exception) {
            return Resource.error("No data" , null)
        }
    }
}