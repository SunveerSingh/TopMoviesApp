package io.github.sunveersingh.topmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.sunveersingh.topmovies.model.MoviesAdapter
import io.github.sunveersingh.topmovies.model.details
import io.github.sunveersingh.topmovies.model.tmdbapi
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class home : AppCompatActivity() {
    val base_url = "https://api.themoviedb.org"
    val api_key = "f4ae53d60330375a486028e73667c90c"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val retrofir = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val tmdb = retrofir.create(tmdbapi::class.java)
        val call: Call<details> = tmdb.getMovies(api_key)
        call.enqueue(object: Callback<details>{
            override fun onFailure(call: Call<details>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<details>, response: Response<details>) {
                if (response.isSuccessful){
                    recylerview.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(applicationContext)
                        adapter = MoviesAdapter(response.body()!!.results, context)
                    }
                }
            }
        })
    }
}

