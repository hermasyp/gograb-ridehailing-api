package com.catnip.ridehailing.service.user

import com.catnip.ridehailing.component.config.authentification.JwtConfig
import com.catnip.ridehailing.component.config.exception.AppException
import com.catnip.ridehailing.entity.login.LoginRequest
import com.catnip.ridehailing.entity.login.LoginResponse
import com.catnip.ridehailing.entity.user.User
import com.catnip.ridehailing.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(@Autowired private val userRepository: UserRepository) : UserServices {
    override fun login(request: LoginRequest): Result<LoginResponse> {
        val user = userRepository.getUserByUsername(request.username)
        return user.map {
            val token = JwtConfig.generateToken(it)
            val userPassword = it.password
            if(userPassword == request.password){
                LoginResponse(it,token)
            }else{
                throw AppException("Wrong username or Password")
            }
        }
    }

    override fun register(user: User): Result<Boolean> {
        val isUsernameExist = userRepository.getUserByUsername(user.username).getOrNull() != null
        if(isUsernameExist){
            throw AppException("Username already Exist !")
        }else{
            return userRepository.createUser(user)
        }
    }

    override fun updateUser(userId: String, user: User): Result<User> {
        return userRepository.updateUser(userId, user)
    }

    override fun getUserByUserId(id: String): Result<User> {
        return userRepository.getUserById(id)
    }

    override fun getUserByUsername(username: String): Result<User> {
        return userRepository.getUserByUsername(username)
    }

}

interface UserServices {
    fun login(userLogin: LoginRequest): Result<LoginResponse>
    fun register(user: User): Result<Boolean>
    fun updateUser(userId : String,user: User): Result<User>
    fun getUserByUserId(id: String): Result<User>
    fun getUserByUsername(username: String): Result<User>
}