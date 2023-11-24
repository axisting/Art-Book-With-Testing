package com.axisting.artbookwithtesting.model

import com.google.gson.annotations.SerializedName

data class ImageResult(
    var id : Int,
    var previewWidth : Int,
    var previewHeight : Int,
    var webformatWidth : Int,
    var webformatHeight : Int,
    var imageWidth : Int,
    var imageHeight : Int,
    var imageSize : Int,
    var views : Int,
    var downloads : Int,
    var collections : Int,
    var likes  : Int,
    var comments : Int,
    @SerializedName("user_id" )
    var userId : Int,
    var user : String,
    var userImageURL : String,
    var pageURL : String,
    var type : String,
    var tags : String,
    var previewURL : String,
    var webformatURL : String,
    var largeImageURL : String


)
