package com.plcoding.cryptocurrencyappyt.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {
    private val _coinsState = mutableStateOf(CoinListState())
    val coinState: State<CoinListState> = _coinsState

    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _coinsState.value = CoinListState(
                        isLoading = true,
                        coins = listOf(),
                        error = ""
                    )
                }

                is Resource.Success -> {
                    _coinsState.value = CoinListState(
                        isLoading = false,
                        coins = result.data ?: emptyList(),
                        error = ""
                    )
                }

                is Resource.Error ->
                    _coinsState.value = CoinListState(
                        isLoading = false,
                        coins = listOf(),
                        error = result.message ?: "Unexpected Error"
                    )
            }
        }.launchIn(viewModelScope)
    }
}