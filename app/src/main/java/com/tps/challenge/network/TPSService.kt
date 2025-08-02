package com.tps.challenge.network

import com.tps.challenge.network.model.CountryResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Communicates with the walmart json backend to obtain data using coroutines.
 */
interface TPSCoroutineService {
    /**
     * Returns the feed per location provided.
     */
    @GET("countries.json")
    suspend fun getCountryInfo(): List<CountryResponse>

}
