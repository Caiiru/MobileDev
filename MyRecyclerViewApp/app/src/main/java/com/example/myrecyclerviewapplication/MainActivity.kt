package com.example.myrecyclerviewapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myrecyclerviewapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var cityDAO: CityDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main)
        binding.mainRecyclerView.adapter =
            CityAdapter(object : CityAdapter.OnCityClickListener{
                override fun onCityClick(view: View, position: Int) {
                    editCity(position)

                }

                override fun onCityLongClick(view: View, position: Int) {
                    Singleton.cities.removeAt(position)
                    binding.
                    mainRecyclerView.
                    adapter?.notifyItemRemoved(position)
                }
            })

        val database = CityDatabaseObject.getDatabase(this)
        //cityDAO = database.cityDAO()
        /*
      GlobalScope.launch( Dispatchers.IO) {
          val city = City(1,"Cidade 1",100,false )
          cityDAO.insertAll(city)


          val cities = cityDAO.getAll()
          cities.forEach{
              println("City: ${it.name}")
          }
      }
      */


        for (i in 0..10){
            Singleton.cities.add(
                City(i.toLong(),"City $i",i)
            )
        }
        binding.mainRecyclerView.layoutManager =
            LinearLayoutManager(this)
        //binding.mainRecyclerView.setOn
        binding.addButton.setOnClickListener {
            Singleton.cities.add(
               City(Singleton.cities.size.toLong(),"City Added",1000)
            )
            binding.mainRecyclerView.adapter?.notifyItemInserted(
                Singleton.cities.size - 1
            )
        }
    }

    fun editCity(cityPosition:Int){
        val intentEditCity = Intent(this, EditCityActivity::class.java)
        intentEditCity.putExtra("position",cityPosition)
        startActivity(intentEditCity)
    }
}