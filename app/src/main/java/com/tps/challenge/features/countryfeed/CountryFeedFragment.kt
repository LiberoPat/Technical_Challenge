package com.tps.challenge.features.countryfeed

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tps.challenge.R
import com.tps.challenge.network.CountryFeedViewModelFactory
import com.tps.challenge.network.CountryFeedViewModelProvider
import com.tps.challenge.network.FetchCountriesUseCase
import com.tps.challenge.network.createTPSService
import com.tps.challenge.network.repository.CountryRepository
import kotlinx.coroutines.launch

/**
 * Displays the list of Countries with its title, description to the user.
 */
class CountryFeedFragment : Fragment() {

    private val countryFeedAdapter by lazy { CountryFeedAdapter() }
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CountryFeedViewModel
    private var savedScrollState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = CountryFeedViewModelProvider.provideFactory()

        viewModel = ViewModelProvider(requireActivity(), factory)[CountryFeedViewModel::class.java]
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("scroll_state", recyclerView.layoutManager?.onSaveInstanceState())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_feed, container, false)

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_container)
        val errorMessage = view.findViewById<TextView>(R.id.error_message)
        recyclerView = view.findViewById(R.id.countries_view)

        savedScrollState = savedInstanceState?.getParcelable("scroll_state")

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = countryFeedAdapter
        }



        swipeRefreshLayout.setOnRefreshListener {
            if (requireContext().isNetworkAvailable()) {
                errorMessage.visibility = View.GONE
                viewModel.fetchCountries()
            } else {
                swipeRefreshLayout.isRefreshing = false
                errorMessage.visibility = View.VISIBLE
            }
        }

        // Show error initially if no network
        if (!requireContext().isNetworkAvailable()) {
            errorMessage.visibility = View.VISIBLE
        } else {
            errorMessage.visibility = View.GONE
            viewModel.fetchCountries()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.countryFeed.collect { countries ->
                        countryFeedAdapter.submitList(countries) {
                            savedScrollState?.let { state ->
                                recyclerView.layoutManager?.onRestoreInstanceState(state)
                                savedScrollState = null
                            }
                        }
                    }
                }
                launch {
                    viewModel.isLoading.collect { loading ->
                        swipeRefreshLayout.isRefreshing = loading
                    }
                }
            }
        }
    }
}
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }