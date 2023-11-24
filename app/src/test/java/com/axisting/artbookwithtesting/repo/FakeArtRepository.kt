package com.axisting.artbookwithtesting.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.axisting.artbookwithtesting.model.Art
import com.axisting.artbookwithtesting.model.ImageResponse
import com.axisting.artbookwithtesting.util.Resource

class FakeArtRepository : ArtRepositoryInterface {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refleshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refleshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf() , 0 , 0 ))
    }

    fun refleshData (){
        artsLiveData.postValue(arts)
    }
}