package com.catnip.ridehailing.model.dto.register

import com.catnip.ridehailing.model.entity.role.Role
import com.catnip.ridehailing.model.entity.user.User
import org.litote.kmongo.Id

data class RegisterRequest(val username: String, val password: String)


fun RegisterRequest.mapToUser(roleId : Id<Role>?) : User {
    return User(username = username, password = password, roleId = roleId)
}