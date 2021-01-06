package com.example.testcermati.api

import retrofit2.Response
import com.example.testcermati.data.Result

abstract class BaseDataSource {

  protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
    try {
      val response = call()
      if (response.isSuccessful) {
        val body = response.body()
        if (body != null) return Result.success(body)
      }
      return error(" ${response.code()} ${response.message()}")
    } catch (e: Exception) {
      return error(e.message ?: e.toString())
    }
  }

  private fun <T> error(message: String): Result<T> {
    return Result.error("Network call has failed for a following reason: $message")
  }

}
