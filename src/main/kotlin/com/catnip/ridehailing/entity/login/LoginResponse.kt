package com.catnip.ridehailing.entity.login

import com.catnip.ridehailing.entity.user.User

data class LoginResponse(val user: User, val token: String)