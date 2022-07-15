package com.catnip.ridehailing.entity.user

import com.catnip.ridehailing.entity.role.Role
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class User(var _id : Id<User> = newId(), var username: String = "", var password: String = "", var roleId: Id<Role>?)