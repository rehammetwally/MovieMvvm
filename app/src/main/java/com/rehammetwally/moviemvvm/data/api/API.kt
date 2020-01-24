package com.rehammetwally.moviemvvm.data.api

import com.rehammetwally.moviemvvm.data.model.MovieDetails
import com.rehammetwally.moviemvvm.data.model.PopularMovies
import io.reactivex.Observer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface API {
    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page:Int):Single<PopularMovies>
    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id:Int):Single<MovieDetails>
}