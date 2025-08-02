package com.tps.challenge.network.repository

import com.tps.challenge.network.TPSCoroutineService
import com.tps.challenge.network.model.CountryResponse

class CountryRepository(
    private val tpsCoroutineService: TPSCoroutineService
) {
    suspend fun getCountryFeed(): List<CountryResponse> {
        return tpsCoroutineService.getCountryInfo()
    }
}