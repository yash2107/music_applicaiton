package com.example.musicapplicaiton

import com.example.musicapplicaiton.data.MusicApiResponse
import com.example.musicapplicaiton.data.Songs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteSource @Inject constructor(private val musicApi: MusicApi) {

    suspend fun fetchSongs():Response<MusicApiResponse> = withContext(Dispatchers.IO){
        return@withContext musicApi.fetchSongs()
    }
}