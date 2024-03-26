package com.example.loginpage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
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

class MainActivity : AppCompatActivity() {

    //private val repository = UserRepository()

    //private val repository = intent.getSerializableExtra("repositoryRegistered") as? UserRepository

    //private val controller = UserController(UserService(repository)?
    private lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
         if(savedInstanceState!=null && savedInstanceState.containsKey("repository")) {
             repository = savedInstanceState.getSerializable("repository") as UserRepository
             Log.d("DEBUG","Repository Getted by before instance")
         }else{
             val repositoryFromIntent = intent.getSerializableExtra("repository") as? UserRepository
             if(repositoryFromIntent != null){
                 repository = repositoryFromIntent
                 Log.d("DEBUG", "Repository Received by Another Activity")
             }
             else {
                 repository = UserRepository()
                 Log.d("DEBUG", "Repository Created")
             }
         }


        val userController = UserController(UserService(repository))

        val loginButton = findViewById<Button>(R.id.loginButton)
        val userName = findViewById<TextView>(R.id.userEditText)
        val userPassword = findViewById<TextView>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)


        userName.text = ""
        userPassword.text = ""


        registerButton.setOnClickListener{
            val intentRegister = Intent(this,RegisterUser::class.java)
            intentRegister.putExtra("repository",repository)
            startActivity(intentRegister)

        }
        loginButton.setOnClickListener{
            if(userController.login(userName.text.toString(),userPassword.text.toString()) != null){
                Toast.makeText(this,"Usuario ${userName.text.toString()} logou", Toast.LENGTH_SHORT).show()
                val intentLogin = Intent(this, UpdateUser::class.java)
                intentLogin.putExtra("repository",repository)
                intentLogin.putExtra("userName",userName.text.toString())
                startActivity(intentLogin)
            }
            else{
                Toast.makeText(this,"Usuario Invalido",Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putSerializable("reposiotyr",repository)
    }


}