package com.aowen.fve.network

import retrofit2.HttpException
import retrofit2.Response

sealed class FveResult<out T, out E> {
    class Success<S>(val data: S) : FveResult<S, Nothing>()
    class Error<E>(val error: E) : FveResult<Nothing, E>()
}

/**
 * Map on success.
 */
inline fun <T, TT, E> FveResult<T, E>.map(onSuccess: (T) -> TT): FveResult<TT, E> {
    return when (this) {
        is FveResult.Success -> FveResult.Success(onSuccess(this.data))
        is FveResult.Error -> this
    }
}

/**
 * Map on success, optionally map to a new error.
 */
inline fun <T, TT, E> FveResult<T, E>.flatMap(
    onSuccess: (T) -> FveResult<TT, E>
): FveResult<TT, E> {
    return when (this) {
        is FveResult.Success -> onSuccess(this.data)
        is FveResult.Error -> this
    }
}

/**
 * Map on error.
 */
inline fun <T, E, EE> FveResult<T, E>.mapError(onError: (E) -> EE): FveResult<T, EE> {
    return when (this) {
        is FveResult.Success -> this
        is FveResult.Error -> FveResult.Error(onError(this.error))
    }
}

inline fun <T, TT, E, EE> FveResult<T, E>.mapAndThrowOnError(
    onSuccess: (T) -> TT,
    onError: (E) -> EE
): FveResult<TT, EE> {
    return when (this) {
        is FveResult.Success -> FveResult.Success(onSuccess(this.data))
        is FveResult.Error -> FveResult.Error(onError(this.error))
    }
}

/**
 * A safe api call that wraps the response in a [NetworkResult]
 * This allows us to handle the different states of the response
 * in a more custom way.
 * @param apiCall The api call to make
 */
suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): FveResult<T, FveCommonError> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            FveResult.Success(body)
        } else {
            FveResult.Error(FveCommonError.NetworkError(
                code = response.code(),
                message = response.message()
            ))
        }
    } catch (e: HttpException) {
        FveResult.Error(FveCommonError.NetworkError(
            code = e.code(),
            message = e.localizedMessage
        ))
    } catch (e: Throwable) {
        FveResult.Error(FveCommonError.UnknownError(e.localizedMessage))
    }
}