package com.catnip.ridehailing.utils.ext

import com.catnip.ridehailing.component.config.exception.AppException
import com.catnip.ridehailing.wrapper.BaseResponse

inline fun <reified T> T?.toResult(
        message: String = "${T::class.simpleName} is null"
): Result<T> {
    return if (this != null) {
        Result.success(this)
    } else {
        Result.failure(AppException(message))
    }
}

fun <T> Result<T>.toResponses(): BaseResponse<T> {
    return if (this.isFailure) {
        throw AppException(this.exceptionOrNull()?.message ?: "Failure")
    } else {
        BaseResponse.success(this.getOrNull())
    }
}