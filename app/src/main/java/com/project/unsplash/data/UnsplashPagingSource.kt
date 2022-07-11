package com.project.unsplash.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.project.unsplash.data.api.UnsplashApi
import com.project.unsplash.models.UnsplashImage
import com.project.unsplash.util.Constants.Companion.UNSPLASH_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

//this class for return results page by page
class UnsplashPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String //we dont use inject here cuz they query known in compile time so we cant inject it
) : PagingSource<Int, UnsplashImage>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        //this fun trigger the api request and turn the data into pages
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhotos(query, position, params.loadSize)
            val images = response.results
            LoadResult.Page(
                data = images,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (images.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            //when there is no internet connection
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            //when something go wrong in the server
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        TODO("Not yet implemented")
    }
}