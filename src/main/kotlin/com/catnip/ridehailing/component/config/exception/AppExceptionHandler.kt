package com.catnip.ridehailing.component.config.exception

import com.catnip.ridehailing.wrapper.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class AppExceptionHandler : ResponseEntityExceptionHandler(){
    @ExceptionHandler(value = [AppException::class])
    fun handleThrowable(throwable: AppException) : ResponseEntity<BaseResponse<Nothing>>{
        return ResponseEntity(BaseResponse.failure(throwable.message ?: "Failure"), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}