package com.project.unsplash.ui.images

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.project.unsplash.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val images = currentQuery.switchMap {
        //this will be excuted whenever the currentQuery changed
        queryString -> repository.getResults(queryString).cachedIn(viewModelScope) //to cache this live data and not getting crash when rotate the mobile
    }

    fun searchImages(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "penguin"
    }
}