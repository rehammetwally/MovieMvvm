package com.rehammetwally.moviemvvm.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rehammetwally.moviemvvm.data.api.API
import com.rehammetwally.moviemvvm.data.model.Movie
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory(
    private val api: API,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {
    val movieLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {

        val movieDataSource = MovieDataSource(api, compositeDisposable)
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}