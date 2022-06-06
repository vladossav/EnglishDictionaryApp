package com.example.tensor_project.api

import com.example.tensor_project.model.WordItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit

import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("{word}")
    suspend fun getWord(
        @Path("word") word: String
    ):  Response<List<WordItem>>

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/"

        fun create() : DictionaryApi {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory()).build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(DictionaryApi::class.java)
        }

    }
}

