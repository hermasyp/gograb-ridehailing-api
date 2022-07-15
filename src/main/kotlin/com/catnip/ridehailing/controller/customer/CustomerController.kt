package com.catnip.ridehailing.controller.customer

import com.catnip.ridehailing.component.config.exception.AppException
import com.catnip.ridehailing.model.dto.login.LoginRequest
import com.catnip.ridehailing.model.dto.login.LoginResponse
import com.catnip.ridehailing.model.dto.register.RegisterRequest
import com.catnip.ridehailing.model.dto.register.mapToUser
import com.catnip.ridehailing.model.dto.user.UserResponse
import com.catnip.ridehailing.model.entity.user.User
import com.catnip.ridehailing.service.role.RoleServices
import com.catnip.ridehailing.service.user.UserServices
import com.catnip.ridehailing.utils.constants.ApiConstants
import com.catnip.ridehailing.utils.constants.RoleConstants
import com.catnip.ridehailing.utils.ext.toResponses
import com.catnip.ridehailing.wrapper.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${ApiConstants.BASE_URL_API}/customer")
class CustomerController {
    @Autowired
    private lateinit var userServices: UserServices

    @Autowired
    private lateinit var roleServices: RoleServices

    @GetMapping
    fun getUser(): BaseResponse<UserResponse> {
        val userId = SecurityContextHolder.getContext().authentication.principal as? String
        return userServices.getUserByUserId(userId.orEmpty()).toResponses()
    }

    @PostMapping("/login")
    fun login(
            @RequestBody loginRequest: LoginRequest
    ): BaseResponse<LoginResponse> {
        return userServices.login(loginRequest).toResponses()
    }

    @PostMapping("/register")
    fun register(
            @RequestBody registerRequest: RegisterRequest
    ): BaseResponse<Boolean> {
        val role = roleServices.findRoleByTag(RoleConstants.ROLE_USER)
        if (role.isSuccess) {
            return userServices.register(registerRequest.mapToUser(role.getOrNull()?._id)).toResponses()
        } else {
            throw AppException("Role Not Found")
        }
    }
}