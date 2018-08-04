package com.ahmedismail.kapp.presentation.features.home

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedismail.kapp.R
import com.ahmedismail.kapp.domain.usecases.withSearchableName
import com.ahmedismail.kapp.entities.City
import com.ahmedismail.kapp.presentation.components.hideKeyboard
import com.ahmedismail.kapp.presentation.components.toMutableLiveData
import com.ahmedismail.kapp.presentation.components.withPorts
import com.ahmedismail.kapp.presentation.components.withTextWatcher
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

private const val EMPTY_TEXT = ""

class HomeActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bindViews()
    }
}

class HomeViewModel(
        val loading: MutableLiveData<Boolean> = false.toMutableLiveData,
        val searchInput: MutableLiveData<String> = EMPTY_TEXT.toMutableLiveData,
        val searchResult: MutableLiveData<List<City>> = ArrayList<City>().toMutableLiveData,
        var searchInProgress: Deferred<List<City>>? = null) : ViewModel()


private fun HomeActivity.bindViews() = with(viewModel) {

    loading.observe(this@bindViews,
            Observer { home_progress_bar.visibility = if (it) VISIBLE else INVISIBLE })

    home_search_editText.withTextWatcher(searchInput::setValue)

    home_search_button.setOnClickListener {
        it.hideKeyboard()
        startSearch()
    }

    with(home_favorites_recycler_view) {
        layoutManager = LinearLayoutManager(this@bindViews)
        adapter = SearchResultsAdapter(searchResult, this@bindViews)
        setOnTouchListener { _, _ ->
            hideKeyboard()
            return@setOnTouchListener false
        }
    }

}

private fun HomeActivity.startSearch() = withPorts {
    withDatabase { citiesTable::queryCityByName }
            .also { viewModel.searchForCity(it) }
}


suspend fun HomeViewModel.searchForCity(cityFinder: (String) -> List<City>) {
    loading.postValue(true)
    searchInProgress?.cancel()
    searchInProgress = async {
        cityFinder.withSearchableName(searchInput.value)()
                .also { loading.postValue(false) }
    }
    searchResult.postValue(searchInProgress?.await())

}



