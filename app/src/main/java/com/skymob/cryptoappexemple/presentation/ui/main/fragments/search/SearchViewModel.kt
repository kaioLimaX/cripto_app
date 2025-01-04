package com.skymob.cryptoappexemple.presentation.ui.main.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymob.cryptoappexemple.domain.remote.model.Crypto
import com.skymob.cryptoappexemple.domain.remote.usecases.SearchCryptoUseCase
import com.skymob.cryptoappexemple.presentation.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchCryptoUseCase: SearchCryptoUseCase
) : ViewModel() {

    private val _searchResults = MutableLiveData<UiState<List<Crypto>>>()
    val searchResults: LiveData<UiState<List<Crypto>>> get() = _searchResults

    private val _searchQuery = MutableStateFlow("")

    init {
        observeSearchQuery() // Inicia a observação do fluxo no init
    }

    // Atualiza o termo de busca
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Observa o termo de busca com debounce e filtros
    private fun observeSearchQuery() {
        _searchQuery
            .debounce(300) // Aguarda o usuário parar de digitar por 300ms
            .filter { it.isNotBlank() } // Ignora buscas vazias
            .distinctUntilChanged() // Evita buscas repetitivas
            .onEach { searchCrypto(it) }
            .launchIn(viewModelScope) //
    }

    private fun searchCrypto(searchTerm: String) {
        viewModelScope.launch {
            _searchResults.value = UiState.Loading
            searchCryptoUseCase.execute(searchTerm).collect { result ->
                result.onSuccess { cryptos ->
                    if (cryptos.isEmpty()) {
                        _searchResults.value = UiState.Empty
                    } else {
                        _searchResults.value = UiState.Success(cryptos)
                    }
                }.onFailure { error ->
                    _searchResults.value = UiState.Error("Erro ao buscar criptomoedas", error)
                }
            }
        }
    }
}