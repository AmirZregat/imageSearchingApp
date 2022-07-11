package com.project.unsplash.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.project.unsplash.data.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val unsplashApi: UnsplashApi) {

    fun getResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ), pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query) }
        ).liveData
}