package com.fappslab.core.usecase.base

sealed class ResultStatus<out T> {

    object Loading : ResultStatus<Nothing>()
    data class Success<out T>(val data: T) : ResultStatus<T>()
    data class Error(val throwable: Throwable) : ResultStatus<Nothing>()

    override fun toString() = when (this) {
        Loading -> "Loading"
        is Success -> "Success[data=$data]"
        is Error -> "Error[throwable=$throwable]"
    }
}
