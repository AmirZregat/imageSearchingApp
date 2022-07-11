package com.project.unsplash.util

import com.project.unsplash.BuildConfig

class Constants {
    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY
        const val UNSPLASH_STARTING_PAGE_INDEX = 1
    }
}