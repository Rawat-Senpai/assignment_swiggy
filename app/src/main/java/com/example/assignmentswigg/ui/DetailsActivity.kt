package com.example.assignmentswigg.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.bumptech.glide.Glide
import com.example.assignmentswigg.R
import com.example.assignmentswigg.databinding.ActivityDetailsBinding
import com.example.assignmentswigg.databinding.ActivityMainBinding
import com.example.assignmentswigg.models.MovieDetailResponseModel
import com.example.assignmentswigg.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private var _binding: ActivityDetailsBinding?= null
    private val binding get()=_binding!!

    private val movieViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_details)

        _binding= ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindObserver()

        val imdbId =  intent.getStringExtra("imdb")
        Log.d("dataCheck",imdbId.toString())

        movieViewModel.getMovieDetails(imdbId.toString())

    }

    private fun bindViews(movie :MovieDetailResponseModel) {
        binding.apply {

            Log.d("checkingDataView",movie.toString())


            titleTextView.text = movie.Title
            yearTextView.text = movie.Year
            ratedTextView.text = movie.Rated
            runtimeTextView.text= movie.Runtime
            genreTextView.text = movie.Genre
            directorTextView.text = movie.Director
            writerTextView.text= movie.Writer
            actorsTextView.text = movie.Actors

            plotTextView.text= movie.Plot
            languageTextView.text = movie.Language
            countryTextView.text = movie.Country
            awardsTextView.text = movie.Awards
            Glide.with(posterImageView).load(movie.Poster).into(posterImageView)
            metascoreTextView.text= movie.Metascore
            imdbRatingTextView.text = movie.imdbRating
            imdbVotesTextView.text = movie.imdbVotes
            imdbIDTextView.text = movie.imdbID
            typeTextView.text = movie.Type
            dvdTextView.text = movie.DVD
            boxOfficeTextView.text = movie.BoxOffice
            productionTextView.text = movie.Production
            websiteTextView.text = movie.Website
            responseTextView.text = movie.Response


        }
    }

    private fun bindObserver() {
        movieViewModel.getMovieDetails.observe(this, Observer {
            binding.progressBar.isVisible= true
            when(it){
                is NetworkResult.Error -> {
                    Log.d("errorData",it.message.toString())

                    Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {

                    Log.d("checkingData",it.data.toString())

                        binding.progressBar.isVisible= false
                        bindViews(it.data!!)
                }
                null -> {

                }
            }

        })


    }

}