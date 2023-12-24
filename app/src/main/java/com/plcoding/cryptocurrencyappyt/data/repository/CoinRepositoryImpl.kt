package com.plcoding.cryptocurrencyappyt.data.repository

import com.plcoding.cryptocurrencyappyt.data.remote.CoinPaprikaApi
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoin
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoinDetail
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import javax.inject.Inject


class CoinRepositoryImpl @Inject constructor(
    private val coinPaprikaApi: CoinPaprikaApi
): CoinRepository {
    override suspend fun getCoins(): List<Coin> {
        return coinPaprikaApi.getCoins().map {
            it.toCoin()
        }
    }

    override suspend fun getCoinById(coinId: String): CoinDetail {
        return coinPaprikaApi.getCoin(coinId = coinId).toCoinDetail()
    }
}