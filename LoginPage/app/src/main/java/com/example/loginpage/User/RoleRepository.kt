package com.example.loginpage.User

class RoleRepository {

    var roles:MutableList<Role> = mutableListOf()

    fun insertRole(roleName:String){
        val roleToInsert = Role(roles.size.toLong(),roleName)
        roles.add(roleToInsert)
    }

    fun findRoleById(id:Long):Role?{
        if(id.toInt()>roles.size) return null

        val sortedRoles = roles.sortedBy { it.id }
        return sortedRoles.elementAt(id.toInt())

    }

    fun getRolesByName():List<Role>{
        return roles.sortedBy { it.name }
    }

}