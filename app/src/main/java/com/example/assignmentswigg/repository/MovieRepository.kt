package com.example.assignmentswigg.repository

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignmentswigg.api.MovieApi
import com.example.assignmentswigg.models.MovieDetailResponseModel
import com.example.assignmentswigg.models.MovieListResponseModel
import com.example.assignmentswigg.utils.Constants
import com.example.assignmentswigg.utils.NetworkResult
import com.example.assignmentswigg.utils.safeApiCall
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApi:MovieApi){

    private val _movieList = MutableLiveData<NetworkResult<MovieListResponseModel>?>()
    val movieList :LiveData<NetworkResult<MovieListResponseModel>?> get()= _movieList



    private val _movieDetails = MutableLiveData<NetworkResult<MovieDetailResponseModel>?>()
    val movieDetails: LiveData<NetworkResult<MovieDetailResponseModel>?> = _movieDetails

    suspend fun  getMovieList(query:String, page:Int=1){

        _movieList.postValue(NetworkResult.Loading())
        val result = safeApiCall { movieApi.getMovieList(Constants.API_KEY,query,page,)}

        _movieList.postValue(result)

    }

    suspend fun getMovieDetails(imdbId:String){
        _movieDetails.postValue(NetworkResult.Loading())
        val result = safeApiCall { movieApi.getMovieDetails(imdbId,Constants.API_KEY) }
        _movieDetails.postValue(result)

    }






}