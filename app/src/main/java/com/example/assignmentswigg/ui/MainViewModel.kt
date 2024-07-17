package com.example.assignmentswigg.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentswigg.models.MovieDetailResponseModel
import com.example.assignmentswigg.models.MovieListResponseModel
import com.example.assignmentswigg.repository.MovieRepository
import com.example.assignmentswigg.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepository: MovieRepository):ViewModel() {


    fun getMovieList(keyword:String){

        viewModelScope.launch {
            movieRepository.getMovieList(keyword,1)
        }

    }

    val getMovieList:LiveData<NetworkResult<MovieListResponseModel>?> get() = movieRepository.movieList


    fun getMovieDetails(imdbId:String){

        viewModelScope.launch {
            movieRepository.getMovieDetails(imdbId)
        }

    }

    val getMovieDetails:LiveData<NetworkResult<MovieDetailResponseModel>?> get() = movieRepository.movieDetails


}