package com.catnip.ridehailing.entity.login

import com.catnip.ridehailing.entity.role.Role
import com.catnip.ridehailing.entity.user.User
import org.litote.kmongo.Id

data class RegisterRequest(val username: String, val password: String)


fun RegisterRequest.mapToUser(roleId : Id<Role>?) : User {
    return User(username = username, password = password, roleId = roleId)
}