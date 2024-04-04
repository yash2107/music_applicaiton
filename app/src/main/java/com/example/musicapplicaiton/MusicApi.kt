package com.example.musicapplicaiton

import com.example.musicapplicaiton.data.MusicApiResponse
import com.example.musicapplicaiton.data.MusicApiService
import com.example.musicapplicaiton.data.Songs
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicApi @Inject constructor(
    private val musicApiService: MusicApiService
){
    suspend fun fetchSongs():Response<MusicApiResponse> = musicApiService.fetchSongs()
}