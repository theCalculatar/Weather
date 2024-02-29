package com.example.weather

import android.app.StatusBarManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.adapter.DailyAdapter
import com.example.weather.adapter.HourlyAdapter
import com.example.weather.databinding.ActivityScrollingBinding
import com.example.weather.model.Daily
import com.example.weather.model.Hourly
import com.example.weather.ui.HomeViewModel

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val controller = ViewCompat.getWindowInsetsController(binding.root)
        controller?.isAppearanceLightStatusBars = false

        setSupportActionBar(binding.toolbar)
        binding.toolbarLayout.title = title
        binding.root.background = getDrawable(R.drawable.person)

//        window?.statusBarColor = getColor(R.color.purple_200)

        val currentTemp = binding.scrollView.currentTemp
        val lowerTemp = binding.scrollView.lowerTemp
        val higherTemp = binding.scrollView.higherTemp
        val feelsLike = binding.scrollView.feelsLike


        //extras
        val precipitaion = binding.scrollView.dailyExtras?.precipitation
        val hum = binding.scrollView.dailyExtras?.hum
        val uv = binding.scrollView.dailyExtras?.uv
        val wind = binding.scrollView.dailyExtras?.wind
        val windDirection = binding.scrollView.dailyExtras?.windDirection
        val pressure = binding.scrollView.dailyExtras?.pressure
        val visibility = binding.scrollView.dailyExtras?.visibility

        val hourlyRecycler = binding.scrollView.hourlyForecastRecycler
        val dailyRecycler = binding.scrollView.dailyForecastRecycler

        hourlyRecycler?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        dailyRecycler?.layoutManager = LinearLayoutManager(this)
        viewModel.data(-23.903081625764052, 29.746948324457588).observe(this){

            "${it.current.temp.toInt()}℃".also { currentTemperature->
                currentTemp?.text = currentTemperature
            }
            "${it.daily[0].temp.max.toInt()}℃".also { maxTemp->
                higherTemp?.text = maxTemp
            }
            "${it.daily[0].temp.min.toInt()}℃".also { minTemp ->
                lowerTemp?.text = minTemp
            }
            "${it.current.feels_like.toInt()}℃".also { minTemp ->
                feelsLike?.text = minTemp
            }
            // layout extras
            "${it.current.humidity}".also { humTxt ->
                hum?.text = humTxt
            }
            "${it.current.visibility}".also { visibilityTxt->
                visibility?.text = visibilityTxt
            }
            "${it.current.pressure}".also { pressureTxt->
                pressure?.text = pressureTxt
            }
            "${it.current.uvi}".also { uvi->
                uv?.text = uvi
            }
            "${it.current.wind_speed}".also { windTxt->
                wind?.text = windTxt
            }
//            "${it.current.}".also { windTxt->
//                wind?.text = windTxt
//            }
            hourlyRecycler?.adapter = HourlyAdapter(it.hourly as ArrayList<Hourly>)
            dailyRecycler?.adapter = DailyAdapter(it.daily as ArrayList<Daily>)

        }
//        val


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}