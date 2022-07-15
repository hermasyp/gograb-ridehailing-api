package com.catnip.ridehailing.model.dto.login

import com.catnip.ridehailing.model.dto.user.UserResponse

data class LoginResponse(val user: UserResponse, val token: String)