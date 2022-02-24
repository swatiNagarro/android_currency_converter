package com.android.currencyconverter.data.base

import com.android.currencyconverter.data.state.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {

        return withContext(Dispatchers.IO) {
            try {
                NetworkResult.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        NetworkResult.Failure(
                            false, throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    }
                    else -> {
                        NetworkResult.Failure(
                            true, 0, null
                        )
                    }
                }
            }
        }
    }
}