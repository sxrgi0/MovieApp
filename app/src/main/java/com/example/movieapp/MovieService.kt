package com.example.movieapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
//169a016e
    @GET("/?apikey=169a016e")
    suspend fun findMovieByName(
        @Query("s") title: String
    ): MovieSearchResponse

    @GET("/?apikey=169a016e")
    suspend fun findMovieById(
        @Query("i") imbdID: String
    ): Movie

    companion object {
        fun getInstance(): MovieService {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            return retrofit.create(MovieService::class.java)

        }
    }
}