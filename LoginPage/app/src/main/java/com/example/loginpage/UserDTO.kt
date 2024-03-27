package com.example.loginpage

import com.example.loginpage.User.Role
import java.io.Serializable

data class UserDTO(var nome:String, var senha:String, val role:Role): Serializable
