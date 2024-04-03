package com.example.consumowebservices

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil as DataBindingUtil
import com.google.gson.Gson
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.Converter.Factory
import com.example.consumowebservices.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher


class MainActivity : AppCompatActivity() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://zenquotes.io/api/quotes")
        .addConverterFactory(JsonConverterFactory.create())
        .build()

    private val api = retrofit.create(QuotesAPI::class.java)

    lateinit var binding:ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.quoteButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val response = api.getQuote()
                binding.quoteText.text = response.value
             }
        }
        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}