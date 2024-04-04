package com.example.musicapplicaiton.Presentation.Screen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.musicapplicaiton.data.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicHomeViewModel @Inject constructor(): BaseViewModel() {

    var state by mutableStateOf(MusicHomeContract.State())

    fun fetchSongs(){
        apiCall(
            api = { remoteSource.fetchSongs() },
            onSuccess = {
                state = state.copy(
                    songList = it.data
                )
            },
            errorHandler = null
        )
    }
}