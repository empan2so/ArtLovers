package com.example.artlovers.data.model

import com.google.gson.annotations.SerializedName

data class ListArtworksResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("data")
    val artworkList: List<ArtworkResponse>
)
