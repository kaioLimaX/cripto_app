package com.skymob.cryptoappexemple.presentation.ui.state

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String, val exception: Throwable? = null) : UiState<Nothing>()
    object Empty : UiState<Nothing>()
}