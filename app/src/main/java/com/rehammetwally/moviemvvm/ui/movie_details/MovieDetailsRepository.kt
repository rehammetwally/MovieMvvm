package com.rehammetwally.moviemvvm.ui.movie_details

import androidx.lifecycle.LiveData
import com.rehammetwally.moviemvvm.data.api.API
import com.rehammetwally.moviemvvm.data.model.MovieDetails
import com.rehammetwally.moviemvvm.data.repository.MovieDetailsNetworkDataSource
import com.rehammetwally.moviemvvm.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val api: API){
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun getMovieDetails(compositeDisposable: CompositeDisposable,id:Int):LiveData<MovieDetails>{
        movieDetailsNetworkDataSource= MovieDetailsNetworkDataSource(api,compositeDisposable)
        movieDetailsNetworkDataSource.getMovieDetails(id)
       return movieDetailsNetworkDataSource.movieDetailsResponse
    }


    fun getMovieDetailsNetworkState():LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }
}