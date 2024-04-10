package com.example.myrecyclerviewapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrecyclerviewapplication.databinding.ActivityEditCityBinding
import com.example.myrecyclerviewapplication.databinding.ActivityMainBinding

class EditCityActivity : AppCompatActivity() {
    lateinit var binding:ActivityEditCityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityEditCityBinding>(
            this, R.layout.activity_edit_city)
        //enableEdgeToEdge()
        //setContentView(R.layout.activity_edit_city)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         */

        val position = intent.getSerializableExtra("position") as Int

        var cityToEdit = Singleton.cities[position]
        binding.CityEditText.text = "Edit City"
        binding.CityNameText.text = "Change City Name: ${cityToEdit.name}"
        binding.PopulationText.text = "Change Population: ${cityToEdit.population}"
        binding.CityNameEdit.setText(cityToEdit.name)
        binding.populationEdit.setText(cityToEdit.population.toString())

        binding.editButton.setOnClickListener {
            Singleton.cities[position].name = binding.CityNameEdit.toString()
            val pop = binding.populationEdit.toString()
            Singleton.cities[position].population = pop.toInt()

            Toast.makeText(this,"${Singleton.cities[position].name} has changed", Toast.LENGTH_SHORT).show()
            val mainIntent = Intent(this,MainActivity::class.java)
            startActivity(mainIntent)
        }

    }
}