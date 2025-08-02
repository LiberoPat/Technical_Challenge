package com.tps.challenge.features.countryfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tps.challenge.R
import com.tps.challenge.network.model.CountryResponse
import androidx.recyclerview.widget.RecyclerView.Adapter

class CountryFeedAdapter : ListAdapter<CountryResponse, CountryItemViewHolder>(CountryDiffCallback()) {
    init {
        setHasStableIds(true)
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }
    override fun getItemId(position: Int): Long {
        return getItem(position).code.hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        val country = getItem(position)
        holder.bind(country)
    }
}

class CountryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val nameText: TextView = itemView.findViewById(R.id.name)
    private val region: TextView = itemView.findViewById(R.id.region)
    private val code: TextView = itemView.findViewById(R.id.code)
    private val capital: TextView = itemView.findViewById(R.id.capital)



    fun bind(country: CountryResponse) {
        nameText.text = country.name
        region.text = itemView.context.getString(R.string.region_with_comma, country.region)
        code.text = country.code
        capital.text = country.capital

    }
}

class CountryDiffCallback : DiffUtil.ItemCallback<CountryResponse>() {
    override fun areItemsTheSame(oldItem: CountryResponse, newItem: CountryResponse): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: CountryResponse, newItem: CountryResponse): Boolean {
        return oldItem == newItem
    }
}
