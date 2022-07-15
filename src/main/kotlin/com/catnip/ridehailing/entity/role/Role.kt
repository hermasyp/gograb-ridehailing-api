package com.catnip.ridehailing.entity.role

import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class Role(val _id : Id<Role> = newId(), val roleName: String, val roleTag: String)
