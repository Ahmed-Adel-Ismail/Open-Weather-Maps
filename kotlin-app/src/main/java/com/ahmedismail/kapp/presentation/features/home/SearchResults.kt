package com.ahmedismail.kapp.presentation.features.home

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ahmedismail.kapp.R
import com.ahmedismail.kapp.entities.City
import java.io.Serializable

private const val INVALID_NAME = "Unknown"

class SearchResultsAdapter(private val searchResult: MutableLiveData<List<City>>,
                           lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<SearchItemViewHolder>() {

    init {
        searchResult.observe(lifecycleOwner, Observer { notifyDataSetChanged() })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_search_city_item, parent, false)
            .run { SearchItemViewHolder(this, context) }

    override fun getItemCount() = searchResult.value?.size ?: 0

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) =
            holder.invalidate(searchResult.value?.get(position))

}

class SearchItemViewHolder(private val view: View, private val context: Context) : RecyclerView.ViewHolder(view) {

    private val name by lazy { view.findViewById<TextView>(R.id.search_item_city_name_textView) }
    private val action by lazy { view.findViewById<Button>(R.id.search_item_city_add_to_favorites_button) }

    fun invalidate(city: City?) {
        name.text = city?.name ?: INVALID_NAME
        action.setOnClickListener { context.sendBroadcast(intent(city)) }
    }

    private fun intent(city: City?) = Intent(context.getString(R.string.ACTION_ADD_TO_FAVORITES))
            .takeIf { city != null }
            ?.putExtra(context.getString(R.string.EXTRA_FAVORITE_CITY), city as Serializable)

}