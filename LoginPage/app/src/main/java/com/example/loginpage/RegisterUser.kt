package com.example.loginpage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginpage.User.Controller.UserController
import com.example.loginpage.User.Repository.UserRepository
import com.example.loginpage.User.Role
import com.example.loginpage.User.RoleRepository
import com.example.loginpage.User.Service.UserService

class RegisterUser : AppCompatActivity() {


    private lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        repository = intent.getSerializableExtra("repository") as UserRepository
        val controller = UserController(UserService(repository))

        var userNameCamp = findViewById<TextView>(R.id.userEditText)
        var passwordCamp = findViewById<TextView>(R.id.passwordEditText)
        var confirmPasswordCamp = findViewById<TextView>(R.id.ConfirmPasswordEditText)

        var roleSelector = findViewById<Spinner>(R.id.roleSelector)

        val roleRepository = RoleRepository()
        roleRepository.insertRole("ADM")
        roleRepository.insertRole("Usuario")
        roleRepository.insertRole("Visitante")

        var rolesToShow:MutableList<String> = mutableListOf()

        for(role in roleRepository.roles){
            rolesToShow.add(role.name)
        }

        Log.d("Roles", rolesToShow.size.toString())
        roleRepository.roles.forEach{
            Log.d("DEBUG",it.name)
        }

        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,rolesToShow).apply{
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        var selectedRoleID = 0
        roleSelector.adapter = adapter
        roleSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@RegisterUser,
                    "Selected: ${roleRepository.roles[position]}",
                    Toast.LENGTH_SHORT).show()
                selectedRoleID = position

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        userNameCamp.text=""
        passwordCamp.text=""
        confirmPasswordCamp.text=""

        val registerButton = findViewById<Button>(R.id.registerButton)


        registerButton.setOnClickListener {

            if(passwordCamp.text.toString() != confirmPasswordCamp.text.toString()){
                Toast.makeText(this@RegisterUser,
                    "Senhas não são iguais",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val userToRegister = UserDTO(
                userNameCamp.text.toString(),
                passwordCamp.text.toString(),
                roleRepository.findRoleById(selectedRoleID.toLong())!!
            )
            Log.d("DEBUG", roleRepository.findRoleById(selectedRoleID.toLong())!!.name)

            var mensagem = ""
            if(controller.insert(userToRegister)){
                mensagem = "Usuario registrado com sucesso"
                userNameCamp.text = ""
                passwordCamp.text = ""
                confirmPasswordCamp.text=""

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("repository",repository)
                startActivity(intent)

            }else{
                mensagem= "Usuario ja existe"
            }

            Toast.makeText(this,mensagem,Toast.LENGTH_SHORT).show()
        }
    }


}