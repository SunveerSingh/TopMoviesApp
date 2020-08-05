package io.github.sunveersingh.topmovies.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface tmdbapi {
    //https://api.themoviedb.org/3/movie/popular?api_key=f4ae53d60330375a486028e73667c90c&language=en-US&page=1
    @GET("/3/movie/popular")
    fun getMovies(@Query("api_key") key: String): Call<details>
}