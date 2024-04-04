package com.example.musicapplicaiton.data

import retrofit2.Response
import retrofit2.http.GET

interface MusicApiService {
    @GET("items/songs")
    suspend fun fetchSongs(): Response<MusicApiResponse>
}