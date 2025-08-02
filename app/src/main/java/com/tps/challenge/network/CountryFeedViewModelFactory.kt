package com.tps.challenge.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tps.challenge.features.countryfeed.CountryFeedViewModel
import com.tps.challenge.network.repository.CountryRepository

class CountryFeedViewModelFactory(
    private val repository: CountryRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryFeedViewModel::class.java)) {
            return CountryFeedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}