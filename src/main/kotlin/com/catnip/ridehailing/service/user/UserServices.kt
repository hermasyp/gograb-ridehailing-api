package com.catnip.ridehailing.service.user

import com.catnip.ridehailing.component.config.authentification.JwtConfig
import com.catnip.ridehailing.component.config.exception.AppException
import com.catnip.ridehailing.model.dto.login.LoginRequest
import com.catnip.ridehailing.model.dto.login.LoginResponse
import com.catnip.ridehailing.model.dto.user.UserResponse
import com.catnip.ridehailing.model.entity.user.User
import com.catnip.ridehailing.model.mapper.toDto
import com.catnip.ridehailing.repository.user.UserRepository
import com.catnip.ridehailing.utils.ext.toResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(@Autowired private val userRepository: UserRepository) : UserServices {
    override fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        val user = userRepository.getUserByUsername(loginRequest.username)
        return user.map {
            val token = JwtConfig.generateToken(it)
            val userPassword = it.password
            if (userPassword == loginRequest.password) {
                LoginResponse(it.toDto(), token)
            } else {
                throw AppException("Wrong username or Password")
            }
        }
    }

    override fun register(user: User): Result<Boolean> {
        val isUsernameExist = userRepository.getUserByUsername(user.username).getOrNull() != null
        if (isUsernameExist) {
            throw AppException("Username already Exist !")
        } else {
            return userRepository.createUser(user)
        }
    }

    override fun updateUser(userId: String, user: User): Result<UserResponse> {
        return userRepository.updateUser(userId, user).map { it.toDto() }
    }

    override fun getUserByUserId(id: String): Result<UserResponse> {
        return userRepository.getUserById(id).map { it.toDto()}
    }

    override fun getUserByUsername(username: String): Result<UserResponse> {
        return userRepository.getUserByUsername(username).map { it.toDto() }
    }

}

interface UserServices {
    fun login(loginRequest: LoginRequest): Result<LoginResponse>
    fun register(user: User): Result<Boolean>
    fun updateUser(userId: String, user: User): Result<UserResponse>
    fun getUserByUserId(id: String): Result<UserResponse>
    fun getUserByUsername(username: String): Result<UserResponse>
}