package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.function.LongFunction

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: MovieAdapter

    var movieList: List<Movie> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = MovieAdapter(movieList) {
            val movie = movieList[it]

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.MOVIE_ID, movie.imdbID)
            startActivity(intent)
        }

        supportActionBar?.title = "Start searching a movie"

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        searchMoviesByName("the+lord")
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)

        val menuItem = menu.findItem(R.id.menu_search)
        val searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMoviesByName(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        return true
    }


    fun searchMoviesByName(query: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val service = MovieService.getInstance()
                val response = service.findMovieByName(query)
                movieList = response.movies

                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(movieList)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}