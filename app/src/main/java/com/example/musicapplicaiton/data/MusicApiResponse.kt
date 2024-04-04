package com.example.musicapplicaiton.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class MusicApiResponse(
    @SerializedName("data") val data: ArrayList<Songs>,
)

data class Songs(
    @SerializedName("id") val id: Long,
    @SerializedName("status") val status: String? = null,
    @SerializedName("sort") val sort: Any?,
    @SerializedName("user_created") val user_created: String? = null,
    @SerializedName("date_created") val date_created: String? = null,
    @SerializedName("user_updated") val user_updated: String? = null,
    @SerializedName("date_updated") val date_updated: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("artist") val artist: String? = null,
    @SerializedName("accent") val accent: String? = null,
    @SerializedName("cover") val cover: String? = null,
    @SerializedName("top_track") val top_track: Boolean? = null,
    @SerializedName("url") val url: String? = null,
)
