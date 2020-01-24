package com.rehammetwally.moviemvvm.ui.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rehammetwally.moviemvvm.data.api.API
import com.rehammetwally.moviemvvm.data.api.POST_PER_PAGE
import com.rehammetwally.moviemvvm.data.model.Movie
import com.rehammetwally.moviemvvm.data.repository.MovieDataSource
import com.rehammetwally.moviemvvm.data.repository.MovieDataSourceFactory
import com.rehammetwally.moviemvvm.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class PopularMovieRepository (private val api: API){
    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun getPopularMovie(compositeDisposable: CompositeDisposable):LiveData<PagedList<Movie>>{
        movieDataSourceFactory= MovieDataSourceFactory(api,compositeDisposable)
       val config=PagedList.Config.Builder()
           .setEnablePlaceholders(false)
           .setPageSize(POST_PER_PAGE)
           .build()

        moviePagedList=LivePagedListBuilder(movieDataSourceFactory,config).build()
        return moviePagedList
    }


    fun getPopularMovieNetworkState():LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource,NetworkState>(
            movieDataSourceFactory.movieLiveDataSource,MovieDataSource::networkState
        )
    }
}