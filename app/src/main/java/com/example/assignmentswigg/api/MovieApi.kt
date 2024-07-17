package com.example.assignmentswigg.api

import com.example.assignmentswigg.models.MovieDetailResponseModel
import com.example.assignmentswigg.models.MovieListResponseModel
import com.example.assignmentswigg.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET(".")
    suspend fun getMovieList(
        @Query("apikey")apiKey:String,
        @Query("s") searchQuery: String,
        @Query("page") page: Int = 1
    ): Response<MovieListResponseModel>

    @GET(".")
    suspend fun getMovieDetails(
        @Query("i") imdbId: String,
        @Query("apikey")apiKey:String
    ): Response<MovieDetailResponseModel>

}
//https://www.omdbapi.com/?i=tt1833673&apikey=2d808700