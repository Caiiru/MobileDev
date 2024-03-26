package com.example.loginpage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginpage.User.Controller.UserController
import com.example.loginpage.User.Repository.UserRepository
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
            val userToRegister = UserDTO(userNameCamp.text.toString(),
                passwordCamp.text.toString())

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