package com.example.assignmentswigg.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentswigg.R
import com.example.assignmentswigg.adapter.MovieListAdapter
import com.example.assignmentswigg.databinding.ActivityMainBinding
import com.example.assignmentswigg.models.MovieListResponseModel
import com.example.assignmentswigg.models.Search
import com.example.assignmentswigg.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding:ActivityMainBinding?= null
    private val binding get()=_binding!!

    private val movieViewModel by viewModels<MainViewModel>()


    lateinit var movieAdapter:MovieListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        _binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        movieAdapter = MovieListAdapter (::onMovieClick)
        binding.rvMovieList.layoutManager = LinearLayoutManager(this)
        binding.rvMovieList.adapter = movieAdapter

        bindObserver()
        bindViews()
    }

    private fun bindViews() {
        binding.apply {
            movieName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val query = s.toString().trim()
                    if (query.isNotEmpty()) {
                        movieViewModel.getMovieList(query)
                    }
                }
            })


        }
    }

    private fun bindObserver() {

        movieViewModel.getMovieList.observe(this, Observer {
            binding.progressBar.isVisible=false
            when(it){
                is NetworkResult.Error -> {
                    Log.d("errorCheck",it.message.toString())
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {

                    binding.progressBar.isVisible= true

                }

                is NetworkResult.Success -> {
                    movieAdapter.submitList(it.data?.Search)
                }
                null -> {}
            }

        })

    }

    private fun onMovieClick(movie: Search){
        Log.d("imdbCheck",movie.toString())
        val intent = Intent(this,DetailsActivity::class.java)
        intent.putExtra("imdb",movie.imdbID.toString())
        startActivity(intent)


    }


}