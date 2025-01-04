package com.skymob.cryptoappexemple.presentation.ui.main.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymob.cryptoappexemple.domain.remote.model.CryptoLists
import com.skymob.cryptoappexemple.domain.remote.usecases.GetCryptoListsUseCase
import com.skymob.cryptoappexemple.presentation.ui.state.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCryptoListsUseCase: GetCryptoListsUseCase
) : ViewModel() {


    private val _cryptoLists = MutableLiveData<UiState<CryptoLists>>()
    val cryptoLists: LiveData<UiState<CryptoLists>> = _cryptoLists

    init {
        loadCryptocurrencies(1, 50, "USD")
    }


    private fun loadCryptocurrencies(start: Int, limit: Int, convert: String) {
        viewModelScope.launch {
            _cryptoLists.postValue(UiState.Loading)
            delay(2000L)
            getCryptoListsUseCase.execute(start, limit, convert)
                .collect { result ->
                    result.onSuccess {
                        _cryptoLists.postValue(UiState.Success(it))

                    }.onFailure {
                        _cryptoLists.postValue(UiState.Error(it.message.toString()))
                    }
                }
        }
    }
}