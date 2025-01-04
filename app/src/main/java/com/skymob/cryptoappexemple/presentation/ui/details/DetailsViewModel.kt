package com.skymob.cryptoappexemple.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymob.cryptoappexemple.domain.local.usecases.CheckFavoriteUseCase
import com.skymob.cryptoappexemple.domain.local.usecases.InsertCryptoFavoriteUseCase
import com.skymob.cryptoappexemple.domain.remote.model.Crypto
import com.skymob.cryptoappexemple.domain.remote.usecases.GetCryptoInfoUseCase
import com.skymob.cryptoappexemple.presentation.ui.state.UiState
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getCryptoInfoUseCase: GetCryptoInfoUseCase,
    private val insertCryptoFavoriteUseCase: InsertCryptoFavoriteUseCase,
    private val checkFavoriteUseCase: CheckFavoriteUseCase
) : ViewModel() {

    private val _cryptoInfo = MutableLiveData<UiState<Crypto>>()
    val cryptoInfo: LiveData<UiState<Crypto>> = _cryptoInfo

    private val _addFavoriteStatus = MutableLiveData<UiState<Crypto>>()
    val addFavoriteStatus: LiveData<UiState<Crypto>> = _addFavoriteStatus

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun addFavorite(crypto: Crypto){
        viewModelScope.launch {
            _addFavoriteStatus.postValue(UiState.Loading)
            val result = insertCryptoFavoriteUseCase.execute(crypto)
            result.onSuccess {
                _addFavoriteStatus.postValue(UiState.Success(crypto))
            }.onFailure {
                _addFavoriteStatus.postValue(UiState.Error(it.message.toString()))
            }
        }
    }

    fun checkIfFavorite(cryptoId: String) {
        viewModelScope.launch {
            val result = checkFavoriteUseCase.execute(cryptoId)
            if (result) {
                _isFavorite.postValue(true)
            } else {
                _isFavorite.postValue(false)
            }
        }
    }



    fun getInfo(id: String) {
        val cryptoId = id.toIntOrNull()
        if (cryptoId == null) {
            _cryptoInfo.postValue(UiState.Error("Invalid ID format"))
            return
        }

        viewModelScope.launch {
            _cryptoInfo.postValue(UiState.Loading)
            val result = getCryptoInfoUseCase.execute(cryptoId)
            result.collect {
                it.onSuccess { crypto ->
                    _cryptoInfo.postValue(UiState.Success(crypto))
                }.onFailure { error ->
                    _cryptoInfo.postValue(UiState.Error(error.message ?: "Unknown error"))
                }
            }
        }
    }


}