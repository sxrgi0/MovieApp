package com.example.movieapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.movieapp.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(var items: List<Movie>, val onItemClick: (position: Int)-> Unit) : Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = items[position]
        holder.render(movie)
        holder.itemView.setOnClickListener {
            onItemClick (position)
        }
    }

    fun updateItems(items: List<Movie>) {
        this.items = items
        notifyDataSetChanged()
    }

}

class MovieViewHolder(val binding: ItemMovieBinding) : ViewHolder(binding.root){

    fun render(movie: Movie){
        binding.titleTextView.text = movie.Title
        Picasso.get().load(movie.Poster).into(binding.posterImageView)
        binding.yearTextView.text = movie.Year

    }

}