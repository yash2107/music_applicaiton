package com.example.musicapplicaiton.Presentation.Screen

import com.example.musicapplicaiton.data.Songs

class MusicHomeContract {
    data class State(
        var songList:ArrayList<Songs> = arrayListOf()
    )
}