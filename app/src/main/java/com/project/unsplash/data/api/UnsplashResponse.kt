package com.project.unsplash.data.api

import com.project.unsplash.models.UnsplashImage

data class UnsplashResponse (
    val results: List<UnsplashImage>
        )