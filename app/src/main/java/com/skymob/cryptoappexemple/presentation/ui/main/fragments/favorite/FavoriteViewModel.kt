package com.skymob.cryptoappexemple.presentation.ui.main.fragments.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymob.cryptoappexemple.domain.local.model.CryptoFavorite
import com.skymob.cryptoappexemple.domain.local.usecases.GetCryptoFavoritesUseCase
import com.skymob.cryptoappexemple.domain.local.usecases.RemoveCryptoFavoriteUseCase
import com.skymob.cryptoappexemple.presentation.ui.state.UiState
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getCryptoFavoritesUseCase: GetCryptoFavoritesUseCase,
    private val removeCryptoFavoriteUseCase: RemoveCryptoFavoriteUseCase

) : ViewModel() {

    private val _cryptoListsFavorites = MutableLiveData<UiState<List<CryptoFavorite>>>()
    val cryptoListsFavorites: LiveData<UiState<List<CryptoFavorite>>> = _cryptoListsFavorites

    init {
        loadCryptoFavorites()
    }


    private fun loadCryptoFavorites() {
        viewModelScope.launch {
            _cryptoListsFavorites.postValue(UiState.Loading)
            getCryptoFavoritesUseCase.execute()
                .onSuccess {
                    _cryptoListsFavorites.postValue(UiState.Success(it))

                }.onFailure {
                    _cryptoListsFavorites.postValue(UiState.Error(it.message.toString()))

                }
        }


    }

    fun reloadList(){
        loadCryptoFavorites()
    }

    fun deleteFavorite(cryptoFavorite: CryptoFavorite) {
        viewModelScope.launch {
            removeCryptoFavoriteUseCase.execute(cryptoFavorite.id).onSuccess {
                loadCryptoFavorites()

            }.onFailure {
                _cryptoListsFavorites.postValue(UiState.Error(it.message.toString()))

            }

        }

    }
}