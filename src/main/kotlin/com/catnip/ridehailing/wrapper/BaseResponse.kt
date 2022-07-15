package com.catnip.ridehailing.wrapper

data class BaseResponse<D>(
        var success: Boolean = true,
        var message: String? = null,
        var data: D? = null
) {

  companion object {
    fun <T> success(data: T?): BaseResponse<T> {
      return BaseResponse(data = data)
    }
    fun <T> failure(message: String): BaseResponse<T> {
      return BaseResponse(success = false, message = message)
    }
  }
}