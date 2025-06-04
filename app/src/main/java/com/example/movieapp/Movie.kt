package com.example.movieapp

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(@SerializedName("Search") val movies: List<Movie>)

data class Movie(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Poster: String,
    val Plot: String,
    val Runtime: String,
    val Director: String,
    val Genre: String,
    val Country: String
)