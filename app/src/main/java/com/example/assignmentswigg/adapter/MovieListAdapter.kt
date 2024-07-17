package com.example.assignmentswigg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignmentswigg.databinding.LayoutMovieListBinding
import com.example.assignmentswigg.models.Search

class MovieListAdapter (private  val onMovieClicked: (Search)->Unit) : ListAdapter<Search, MovieListAdapter.MovieViewHolder>(ComparatorDiffUtil()) {


    inner class MovieViewHolder(private val binding: LayoutMovieListBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(movie: Search){

            binding.apply {

                movieTitle.text = movie.Title
                movieYear.text = movie.Year
                movieImdbId.text= movie.imdbID
                Glide.with(moviePoster).load(movie.Poster).into(moviePoster)
                movieType.text= movie.Type


                root.setOnClickListener{
                    onMovieClicked(movie)
                }
            }




        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<Search>() {
        override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem == newItem
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = LayoutMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movie = getItem(position)
        movie?.let {
            holder.bind(it)
        }

    }
}