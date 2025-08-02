package com.tps.challenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tps.challenge.features.countryfeed.CountryFeedFragment

/**
 * The initial Activity for the app.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countryFeedFragment = CountryFeedFragment()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container, countryFeedFragment)
            .commit()
    }
}
