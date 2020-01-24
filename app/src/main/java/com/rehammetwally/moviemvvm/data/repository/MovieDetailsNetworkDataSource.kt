package com.rehammetwally.moviemvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rehammetwally.moviemvvm.data.api.API
import com.rehammetwally.moviemvvm.data.model.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsNetworkDataSource(
    private val api: API,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState




    private val _movieDetailsResponse = MutableLiveData<MovieDetails>()
    val movieDetailsResponse: LiveData<MovieDetails>
        get() = _movieDetailsResponse



    fun getMovieDetails(id:Int){
        try {

        compositeDisposable.add(
            api.getMovieDetails(id)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _movieDetailsResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDetailsNetDSource",it.message)

                    })
        )
        }catch (e:Exception){
            Log.e("MovieDetailsNetDSource",e.message)

        }
    }

}