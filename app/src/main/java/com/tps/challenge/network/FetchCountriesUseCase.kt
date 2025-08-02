package com.tps.challenge.network

import com.tps.challenge.network.model.CountryResponse
import com.tps.challenge.network.repository.CountryRepository

class FetchCountriesUseCase(
    private val repository: CountryRepository
) {
    suspend fun execute(): List<CountryResponse> {
        return repository.getCountryFeed()
    }
}