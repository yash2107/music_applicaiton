package com.example.musicapplicaiton.data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplicaiton.RemoteSource
import com.example.musicapplicaiton.util.ApiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

open class BaseViewModel:ViewModel() {
    @Inject
    lateinit var remoteSource: RemoteSource

    var apiState by mutableStateOf(ApiState())

    protected fun apiCall(
        api: suspend ()-> Response<MusicApiResponse>,
        onSuccess: suspend (data: MusicApiResponse)->Unit,
        isLoadingRequired:Boolean = true,
        errorHandler: (suspend (error: String) -> Unit)?,
        apiJob:((Job)->Unit)?=null
    ){
        val job: Job?
        if (isLoadingRequired)
            apiState = apiState.copy(isLoaing = true)
        job = viewModelScope.launch {
            try {
                val response = api()
                if (response.isSuccessful && response.body()!=null){
                    response.body()?.let {
                        onSuccess(it)
                    }
                }else{
                    if (errorHandler != null) {
                        errorHandler("Invalid Call")
                    }
                }
            }
            catch (e:Exception){
                Log.e("ApiCall", "Exception occurred: ${e.message}", e)
                apiState = apiState.copy(isLoaing = false)
            }
        }
        apiJob?.invoke(job)
    }
}