package com.plcoding.cryptocurrencyappyt.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Constants
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _coinDetailState = mutableStateOf(CoinDetailState())
    val coinDetailState: State<CoinDetailState> = _coinDetailState

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let {
            getCoinById(it)
        }
    }

    private fun getCoinById(coinId: String) {
        getCoinUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _coinDetailState.value = CoinDetailState(
                        isLoading = true,
                        coinDetail = null,
                        error = ""
                    )
                }

                is Resource.Success -> {//
                    _coinDetailState.value = CoinDetailState(
                        isLoading = false,
                        coinDetail = result.data,
                        error = ""
                    )
                }

                is Resource.Error ->
                    _coinDetailState.value = CoinDetailState(
                        isLoading = false,
                        coinDetail = null,
                        error = result.message ?: "Unexpected Error"
                    )
            }
        }.launchIn(viewModelScope)
    }
}