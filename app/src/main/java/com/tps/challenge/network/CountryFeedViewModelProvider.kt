package com.tps.challenge.network

import androidx.lifecycle.ViewModelProvider
import com.tps.challenge.network.repository.CountryRepository

object CountryFeedViewModelProvider {
    fun provideFactory(): ViewModelProvider.Factory {
        val service: TPSCoroutineService = createTPSService()
        val repository = CountryRepository(service)
        val useCase = FetchCountriesUseCase(repository)
        return CountryFeedViewModelFactory(useCase)
    }
}