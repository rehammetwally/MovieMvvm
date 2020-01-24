package com.rehammetwally.moviemvvm.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rehammetwally.moviemvvm.data.model.MovieDetails
import com.rehammetwally.moviemvvm.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel(private val movieDetailsRepository: MovieDetailsRepository,id:Int):ViewModel() {
    private val compositeDisposable=CompositeDisposable()
    val movieDetails:LiveData<MovieDetails> by lazy{
        movieDetailsRepository.getMovieDetails(compositeDisposable,id)
    }

    val networkState:LiveData<NetworkState> by lazy{
        movieDetailsRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}