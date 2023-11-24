package com.axisting.artbookwithtesting.model

data class ImageResponse(
    var hits : List <ImageResult>,
    var total : Int,
    var totalHits : Int
)
