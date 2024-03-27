package com.example.androidfirstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import androidx.databinding.ViewDataBinding
import com.example.androidfirstapplication.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.databinding.DataBindingUtil as DataBindingUtil1

class MainActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.chucknorris.io")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ChuckNorrisAPI::class.java)
    lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.NorrisButton.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                val responde = api.getRandomJoke()
                binding.textViewHelloWorld.text = responde.value
            }
        }
    }
}