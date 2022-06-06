package com.example.tensor_project.api

import com.example.tensor_project.model.RandomWord
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface RandomWordApi {
   /* @GET("word")
    fun getWord(): Call<List<RandomWord>>*/

    @GET("word")
    suspend fun getWordResponse(): Response<List<RandomWord>>

    companion object {
        const val BASE_URL = "https://random-words-api.vercel.app/"

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        fun create() : RandomWordApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(RandomWordApi::class.java)
        }
    }
}