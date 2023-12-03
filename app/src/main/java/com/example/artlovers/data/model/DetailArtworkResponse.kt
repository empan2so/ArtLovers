package com.example.artlovers.data.model

import com.google.gson.annotations.SerializedName

data class DetailArtworkResponse(
    @SerializedName("data")
    val data: ArtworkResponse
)
