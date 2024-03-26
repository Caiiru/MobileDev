package com.example.loginpage.User.Controller

import com.example.loginpage.User.Service.UserService
import com.example.loginpage.UserDTO

class UserController (private val service:UserService) {



    fun insert(user:UserDTO):Boolean{
        return service.insert(user)
    }

    fun login(userName:String,password:String):UserDTO?{
        val userToLogin = UserDTO(userName,password)
        return service.login(userToLogin)
    }

    fun getUser(userName:String):UserDTO?{
        return service.repository.findByUserName(userName)
    }

    fun deleteUserByNickName(userName:String):Boolean{
        return service.deleteByName(userName)
    }

    fun updateUser(user:UserDTO){

    }

}