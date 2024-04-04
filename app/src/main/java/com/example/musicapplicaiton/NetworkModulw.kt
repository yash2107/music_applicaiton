package com.example.musicapplicaiton

import com.example.musicapplicaiton.data.MusicApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Set logging level
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Add logging interceptor to OkHttpClient
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().disableHtmlEscaping().create()
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://cms.samespace.com/")
            .build()
    }

    @Provides
    @Singleton
    fun provideMusicApiService(retrofit: Retrofit): MusicApiService {
        return retrofit.create(MusicApiService::class.java)
    }
}
