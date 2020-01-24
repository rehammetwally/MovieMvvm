package com.rehammetwally.moviemvvm.ui.popular_movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.GridLayoutManager
import com.rehammetwally.moviemvvm.R
import com.rehammetwally.moviemvvm.data.api.API
import com.rehammetwally.moviemvvm.data.api.MovieDB
import com.rehammetwally.moviemvvm.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.network_state_item.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PopularMovieViewModel
    lateinit var movieRepository: PopularMovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api: API = MovieDB.getInstance()
        movieRepository = PopularMovieRepository(api)
        viewModel = getViewModel()

        val popularMoviesAdapter = PopularMoviesAdapter(this)
        val gridLayout = GridLayoutManager(this, 3)
        gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = popularMoviesAdapter.getItemViewType(position)
                if (viewType == popularMoviesAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3

            }

        }

        popular_movies_rv.layoutManager=gridLayout
        popular_movies_rv.setHasFixedSize(true)
        popular_movies_rv.adapter=popularMoviesAdapter


        viewModel.moviePagedList.observe(this, Observer { popularMoviesAdapter.submitList(it) })
        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility =if(viewModel.listIsEmpty() && it == NetworkState.LOADED) View.VISIBLE else View.GONE
            txt_error_popular.visibility =if(viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()){
                popularMoviesAdapter.setNetworkState(it)
            }

        })

    }

    private fun getViewModel(): PopularMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PopularMovieViewModel(movieRepository) as T
            }
        })[PopularMovieViewModel::class.java]
    }
}
