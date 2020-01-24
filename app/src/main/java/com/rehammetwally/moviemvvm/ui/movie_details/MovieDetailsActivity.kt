package com.rehammetwally.moviemvvm.ui.movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.rehammetwally.moviemvvm.R
import com.rehammetwally.moviemvvm.data.api.API
import com.rehammetwally.moviemvvm.data.api.MovieDB
import com.rehammetwally.moviemvvm.data.api.POSTER_BASE_URL
import com.rehammetwally.moviemvvm.data.model.MovieDetails
import com.rehammetwally.moviemvvm.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_movie_details.*
import java.text.NumberFormat
import java.util.*

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val id: Int = intent.getIntExtra("id",1)

        val api: API = MovieDB.getInstance()
        movieDetailsRepository = MovieDetailsRepository(api)
        viewModel = getViewModel(id)


        viewModel.movieDetails.observe(this, Observer { bindUi(it) })
        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility =if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility =if(it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })


    }

    fun bindUi(it: MovieDetails) {
        Glide.with(this).load(POSTER_BASE_URL+it.posterPath).into(movie_poster)
        movie_title.text=it.title
        movie_tagline.text=it.tagline
        movie_release_date.text=it.releaseDate
        movie_rating.text= it.voteAverage.toString()
        movie_runtime.text= it.runtime.toString()+" minutes"
        movie_overview.text= it.overview

        var formatCurrency= NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text= formatCurrency.format(it.budget)
        movie_revenue.text= formatCurrency.format(it.revenue)

    }

    private fun getViewModel(id: Int): MovieDetailsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailsViewModel(movieDetailsRepository, id) as T
            }
        })[MovieDetailsViewModel::class.java]
    }
}
