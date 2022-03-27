package com.example.topmama.presentation.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.topmama.R
import com.example.topmama.data.models.RoomWeather
import com.example.topmama.domain.models.Weather
import com.example.topmama.utils.AppLocationService
import kotlinx.android.synthetic.main.weather_items.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainViewAdapter(private val weatherItems: List<RoomWeather>, val callBack: CallBack?): RecyclerView.Adapter<MainViewAdapter.MainViewHolder>() {

    var context: Context? = null
    lateinit var weatherData: RoomWeather

   inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        @RequiresApi(Build.VERSION_CODES.O)
        fun setData(weatherItems: RoomWeather){
            itemView.cityName.text = weatherItems.country
//            itemView.currentDate.text = weatherItems.localtime?.let { dateAndTime(it) }
            itemView.currentTime.text = weatherItems.tz_id
            itemView.currentTemperature.text = "${weatherItems.temp_c.toString()} ℃"
            itemView.region.text = weatherItems.region
            itemView.weatherDescription.text = weatherItems.text

            itemView.setOnClickListener(this)

            itemView.weatherFavourite.setOnClickListener {
                itemView.weatherFavourite.setTint(R.color.red)
                callBack?.addToFavourite(weatherItems)
            }
        }

       @RequiresApi(Build.VERSION_CODES.O)
       fun dateAndTime(date: String): String {
           val formatter = DateTimeFormatter.ofPattern("yyyy-MMM-d HH:mm")
           val dateTime = LocalDateTime.parse(date, formatter)

           val dt = dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
           Log.d("TAG", "dateAndTime: $dt")
           return dt
       }

       fun ImageView.setTint(@ColorRes colorRes: Int) {
           ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)))
       }

       override fun onClick(p0: View?) {
           callBack?.moreDetails(weatherData)
       }

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        context = parent.context
//        appLocationService = AppLocationService(context)
        val view = LayoutInflater.from(context).inflate(R.layout.weather_items, parent, false)
        return MainViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        weatherData = weatherItems[position]
        val myImage: Drawable? = ResourcesCompat.getDrawable(context?.resources!!, R.drawable.weather_0, null)
        holder.itemView.mainItemView.background = myImage
        holder.setData(weatherData)

    }

    override fun getItemCount(): Int {
        return weatherItems.size
    }

    interface CallBack {
       fun moreDetails(weatherItems: RoomWeather)
       fun addToFavourite(weatherItems: RoomWeather)
    }

}