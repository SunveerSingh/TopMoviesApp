package io.github.sunveersingh.topmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var timer = object: CountDownTimer(1000, 7000){
            override fun onFinish() {
                val intent = Intent(applicationContext, home::class.java)
                startActivity(intent)
            }

            override fun onTick(millisUntilFinished: Long) {
                //No need of the function yet
            }

        }.start()
    }
}