package com.rehammetwally.moviemvvm.data.model


import com.google.gson.annotations.SerializedName

data class PopularMovies(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movieList: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)