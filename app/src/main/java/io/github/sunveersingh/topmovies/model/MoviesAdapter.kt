package io.github.sunveersingh.topmovies.model

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.sunveersingh.topmovies.R
import java.util.*


class MoviesAdapter(val movies: List<Result>, context: Context): RecyclerView.Adapter<MoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contentview, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        return holder.bind(movies[position])
    }
}

class MoviesViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
    private val photo:ImageView = itemView.findViewById(R.id.movie_photo)
    private val title:TextView = itemView.findViewById(R.id.movie_title)
    private val overview:TextView = itemView.findViewById(R.id.movie_overview)
    private val rating:TextView = itemView.findViewById(R.id.movie_rating)
    private val dateofrelease:TextView = itemView.findViewById(R.id.movie_release_date)
    private val schedulebtn:Button = itemView.findViewById(R.id.schedulebtn)
    var day = 0
    var month = 0
    var year  = 0
    var hour = 0
    var minute = 0
    var savedDay = 0
    var savedMonth = 0
    var savedYear  = 0
    var savedHour = 0
    var savedMinute = 0
    var customTime: Long = 0

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(movie: Result) {
        Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w220_and_h330_face/${movie.poster_path}").into(photo)
        title.text = movie.title
        overview.text = movie.overview
        rating.text = "Rating : "+movie.vote_average.toString()
        dateofrelease.text = "Release Date: ${movie.release_date}"
        schedulebtn.setOnClickListener{
            getDateTimeCalender()
            DatePickerDialog(itemView.context, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth -> onDateSet(view, year, month, dayOfMonth)  }, year, month, day).show()
        }
    }

     fun getDateTimeCalender(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

     fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year
        getDateTimeCalender()
        TimePickerDialog(itemView.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> onTimeSet(view, hourOfDay, minute) }, hour, minute, true).show()
    }

    fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute
        val customCalendar = Calendar.getInstance()
        customCalendar.set(
            savedYear, savedMonth, savedDay, savedHour, savedMinute, 0
        )
        customTime = customCalendar.timeInMillis
        buttonclicknotificationmanager()
        Toast.makeText(itemView.context, "Scheduled at Date: $savedDay-$savedMonth-$savedYear. Time: $savedHour:$savedMinute", Toast.LENGTH_LONG).show()
    }

    fun buttonclicknotificationmanager(){

        val notificationIntent = Intent(itemView.context, brodcast::class.java)
        val broadcast = PendingIntent.getBroadcast(
            itemView.context,
            0,
            notificationIntent,
            FLAG_UPDATE_CURRENT
        )
        val alarmMgr = itemView.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP,customTime,broadcast)
    }
}



