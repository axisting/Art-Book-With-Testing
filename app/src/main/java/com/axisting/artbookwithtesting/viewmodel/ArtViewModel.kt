package com.axisting.artbookwithtesting.viewmodel


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axisting.artbookwithtesting.model.Art
import com.axisting.artbookwithtesting.model.ImageResponse
import com.axisting.artbookwithtesting.repo.ArtRepositoryInterface
import com.axisting.artbookwithtesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ArtViewModel
        @Inject  constructor(
            private val repository : ArtRepositoryInterface
        ): ViewModel() {

        val artList = repository.getArt()

        private val images = MutableLiveData<Resource<ImageResponse>>()
        val imageList : LiveData<Resource<ImageResponse>>
            get() = images


        private val selectedImage = MutableLiveData<String>()
        val selectedImageUrl : LiveData<String>
            get() = selectedImage

        private var insertArtMsg = MutableLiveData<Resource<Art>>()
        val insertArtMessage : LiveData<Resource<Art>>
            get() = insertArtMsg

    fun resetInsertArtMessage (){
        insertArtMsg =  MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage (url : String){
        selectedImage.postValue(url)
    }

    fun deleteArt (art : Art) = viewModelScope.launch{
        repository.deleteArt(art)
    }

    fun insertArt (art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt (name : String , artistName : String , year : String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertArtMsg.postValue(Resource.error("Enter name, artistname, year" , null))
            return
        }
        val yearInt = try {
            year.toInt()
        }catch (e : Exception) {
            insertArtMsg.postValue(Resource.error("Year should be number" , null))
            return
        }

        val art = Art (name , artistName , yearInt , selectedImage.value ?: "" )
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))


    }

    fun searchForImage (searchString : String) {
        if (searchString.isEmpty()) {
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }

    }





}