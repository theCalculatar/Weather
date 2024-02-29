package com.example.weather.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weather.GpsTracker
import com.example.weather.R
import com.example.weather.adapter.DailyAdapter
import com.example.weather.adapter.HourlyAdapter
import com.example.weather.databinding.ActivityScrollingBinding
import com.example.weather.model.Coord
import com.example.weather.model.Daily
import com.example.weather.model.Hourly
import com.google.gson.Gson


class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding
    private var locationPermissionGranted = false
    private lateinit var viewModel:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbarLayout.title = title

        val swipeRefreshLayout = binding.scrollView.refreshLayout
        //
        val currentTemp = binding.scrollView.currentTemp
        val description = binding.scrollView.looksLike
        val lowerTemp = binding.scrollView.lowerTemp
        val higherTemp = binding.scrollView.higherTemp
        val feelsLike = binding.scrollView.feelsLike
        val dewPoint = binding.scrollView.dewPoint


        //extras
        val hum = binding.scrollView.dailyExtras.hum
        val uv = binding.scrollView.dailyExtras.uv
        val wind = binding.scrollView.dailyExtras.wind
        val dew_point = binding.scrollView.dailyExtras.windDirection
        val pressure = binding.scrollView.dailyExtras.pressure
        val visibility = binding.scrollView.dailyExtras.visibility

        val hourlyRecycler = binding.scrollView.hourlyForecastRecycler
        val dailyRecycler = binding.scrollView.dailyForecastRecycler
        val progress = binding.scrollView.progressBar

        swipeRefreshLayout.isRefreshing = true

        hourlyRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        dailyRecycler.layoutManager = LinearLayoutManager(this)

        var coord :Coord? =null
        //initialize location service
        val gpsTracker = GpsTracker(this)
        if (gpsTracker.canGetLocation()) {
            val latitude: Double = gpsTracker.getLatitude()
            val longitude: Double = gpsTracker.getLongitude()
            if (latitude == 0.0){
                val intent = Intent(this,CitySearch::class.java)
                startActivityForResult(intent,1)
            }else{
                coord = Coord(latitude,longitude)
                viewModel.getDataCord(latitude,longitude)
            }
        } else {
            gpsTracker.showSettingsAlert()
        }




        viewModel.error.observe(this){
            binding.toolbarLayout.title = "Weather"
            swipeRefreshLayout.isRefreshing = (it == null)
            it?:return@observe
            it.city?.let { city->
                binding.toolbarLayout.title = city.name
                coord = it.city.coord
            }
            if (it.cod=="404") Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
            if (it.cod=="405") Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
        }

        swipeRefreshLayout.setOnRefreshListener {
            coord?.let { viewModel.getDataCord(it.lat,it.lon)}
        }

        viewModel.data.observe(this){

            "${it.current.temp.toInt()}℃".also { currentTemperature->
                currentTemp.text = currentTemperature
            }
            it.current.weather[0].main.also { currentTemperature->
                description.text = currentTemperature
            }
            "${it.daily[0].temp.max.toInt()}℃".also { maxTemp->
                higherTemp.text = maxTemp
            }
            "${it.daily[0].temp.min.toInt()}℃".also { minTemp ->
                lowerTemp.text = minTemp
            }
            "${it.current.feels_like.toInt()}℃".also { minTemp ->
                feelsLike.text = minTemp
            }
            "${it.current.dew_point.toInt()}℃".also { minTemp ->
                dew_point.text = minTemp
            }
            // layout extras
            "${it.current.humidity}".also { humTxt ->
                hum.text = humTxt
            }
            "${it.current.visibility/1000}km".also { visibilityTxt->
                visibility.text = visibilityTxt
            }
            "${it.current.pressure}hPa".also { pressureTxt->
                pressure.text = pressureTxt
            }
            "${it.current.uvi}".also { uvi->
                uv.text = uvi
            }
            "${it.current.wind_speed}m/s".also { windTxt->
                wind.text = windTxt
            }
            hourlyRecycler.adapter = HourlyAdapter(it.hourly as ArrayList<Hourly>)
            dailyRecycler.adapter = DailyAdapter(it.daily as ArrayList<Daily>)
        }

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
            R.id.action_search -> {
                val intent = Intent(this,CitySearch::class.java)
                startActivityForResult(intent,1)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    /**
     * Handles the result of the request for location permissions.
     */
    // [START maps_current_place_on_request_permissions_result]
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_COURSE_LOCATION ->{
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }else{
                    locationPermissionGranted = false
                    Toast.makeText(this,"Permission Denied!",Toast.LENGTH_LONG)
                        .show()
                }
            }

            else -> {

                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode== 1){
            true->{
                val city = data?.getStringExtra("result")
                if (city != null) {
                    viewModel.getDataCity(city)
                }
            }
            false->{}
        }
    }

    companion object{
        private const val PERMISSIONS_REQUEST_ACCESS_COURSE_LOCATION = 2
    }

}