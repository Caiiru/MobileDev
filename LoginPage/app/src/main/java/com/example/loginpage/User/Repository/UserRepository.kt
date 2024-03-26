package com.example.loginpage.User.Repository

import android.util.Log
import com.example.loginpage.UserDTO
import java.io.Serializable

class UserRepository : Serializable {


    var usersInserted = ArrayList<UserDTO>()

    fun findByUserName(userName:String): UserDTO? {
        if(usersInserted.isEmpty())
            return null

        usersInserted.forEach{
            if(it.nome == userName)
                return it
        }

        return null

    }

    fun insertUser(user: UserDTO):Boolean{
        if(findByUserName(user.nome)!=null){
            return false
        }
        usersInserted.add(user)
        Log.d("Repository","User Inserted: ${user.nome}")
        return true
    }

    fun deleteUserByName(userName:String):Boolean{
        usersInserted.forEach{
            if(it.nome == userName){
                usersInserted.remove(it)
                return true
            }
        }
        return false
    }

}