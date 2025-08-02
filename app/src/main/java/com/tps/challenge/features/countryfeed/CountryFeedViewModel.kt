package com.tps.challenge.features.countryfeed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tps.challenge.network.FetchCountriesUseCase
import com.tps.challenge.network.model.CountryResponse
import com.tps.challenge.network.repository.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryFeedViewModel(
    private val fetchCountriesUseCase: FetchCountriesUseCase
) : ViewModel() {

    private val _countryFeed = MutableStateFlow<List<CountryResponse>>(emptyList())
    val countryFeed: StateFlow<List<CountryResponse>> get() = _countryFeed

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun fetchCountries() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val countries = fetchCountriesUseCase.execute()
                _countryFeed.value = countries
            } catch (e: Exception) {
                Log.e("CountryFeedViewModel", "Failed to fetch countries", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}


