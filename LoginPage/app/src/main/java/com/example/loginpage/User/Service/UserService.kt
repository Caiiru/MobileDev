package com.example.loginpage.User.Service

import com.example.loginpage.User.Repository.UserRepository
import com.example.loginpage.UserDTO

class UserService (val repository:UserRepository) {

    fun insert(user:UserDTO):Boolean{
        return repository.insertUser(user)
    }
    fun login(user: UserDTO):UserDTO?{
        val loginUser = repository.findByUserName(user.nome) ?: return null
        if(loginUser.senha != user.senha)
            return null

        return loginUser
    }

    fun deleteByName(name:String):Boolean{
        return repository.deleteUserByName(name)
    }

    fun updateUser(userEdit:UserDTO){
        val userSave = repository.findByUserName(userEdit.nome)
        if(userSave!=null){
            deleteByName(userEdit.nome)
            insert(userSave)
        }
    }
}