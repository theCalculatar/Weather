package com.example.weather.ui
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.R


class CitySearch: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_city)

        val button = findViewById<TextView>(R.id.id_citySearch)

        button.setOnClickListener {
            val city = findViewById<EditText>(R.id.id_cityEdit)
            val citySearched = city.text.toString()
            val returnIntent = Intent()
            returnIntent.putExtra("result", citySearched)
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }
}