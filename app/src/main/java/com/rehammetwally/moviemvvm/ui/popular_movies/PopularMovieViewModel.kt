package com.rehammetwally.moviemvvm.ui.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rehammetwally.moviemvvm.data.model.Movie
import com.rehammetwally.moviemvvm.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class PopularMovieViewModel(private val popularMovieRepository: PopularMovieRepository):ViewModel() {
    private val compositeDisposable= CompositeDisposable()
    val moviePagedList: LiveData<PagedList<Movie>> by lazy{
        popularMovieRepository.getPopularMovie(compositeDisposable)
    }

    val networkState:LiveData<NetworkState> by lazy{
        popularMovieRepository.getPopularMovieNetworkState()
    }

    fun listIsEmpty():Boolean{
        return moviePagedList.value?.isEmpty() ?:true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}