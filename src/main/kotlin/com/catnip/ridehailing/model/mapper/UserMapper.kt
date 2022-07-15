package com.catnip.ridehailing.model.mapper

import com.catnip.ridehailing.model.dto.user.UserResponse
import com.catnip.ridehailing.model.entity.user.User

fun User.toDto(): UserResponse = UserResponse(
        id = this._id.toString(),
        username = this.username,
        roleId = this.roleId.toString()
)