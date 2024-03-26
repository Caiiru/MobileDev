package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class UpdateUser : AppCompatActivity() {

    private lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        repository = intent.getSerializableExtra("repository") as UserRepository
        val controller = UserController(UserService(repository))
        val userNameToSearch = intent.getSerializableExtra("userName" as String)

        val user = controller.getUser(userNameToSearch.toString())
        if(user!=null){
            Log.d("DEBUG", "FIND USER")
        }
         //user = intent.getSerializableExtra("user")as? UserDTO


        val editNome = findViewById<TextView>(R.id.editUserName)
        val editPassword = findViewById<TextView>(R.id.editPassword)
        val editButton = findViewById<Button>(R.id.editButton)


        user?.let{
            val nome = it.nome
            val senha = it.senha

            editNome.text = nome
            editPassword.text = senha
        }

        editButton.setOnClickListener{
            user?.nome = editNome.text.toString()
            user?.senha = editPassword.text.toString()

            user?.let { it1 -> controller.updateUser(it1) }
            Toast.makeText(this,"Usuario alterado",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("repository", repository )
            startActivity(intent)
        }

    }
}