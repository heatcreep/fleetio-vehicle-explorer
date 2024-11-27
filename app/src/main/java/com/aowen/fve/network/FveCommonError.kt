package com.aowen.fve.network

sealed class FveCommonError {
    data class NetworkError(val code: Int, val message: String?) : FveCommonError()
    data class UnknownError(val message: String?) : FveCommonError()
}