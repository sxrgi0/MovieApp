package com.example.movieapp

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.movieapp.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
    }

    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra(MOVIE_ID)!!
        getMovieId(id)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun getMovieId(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = MovieService.getInstance()
                movie = service.findMovieById(id)

                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun loadData(){
        Picasso.get().load(movie.Poster).into(binding.posterImageView)

        binding.titleTextView.text = movie.Title
        binding.plotTextView.text = movie.Plot
        binding.yearTextView.text = movie.Year
        binding.genreTextView.text = movie.Genre
        binding.directorTextView.text = movie.Director
        binding.runtimeTextview.text = movie.Runtime
        binding.countryTextView.text = movie.Country

        supportActionBar?.title = movie.Title
    }
}