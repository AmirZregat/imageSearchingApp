package com.project.unsplash.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashImage(
    val id: String,
    val description: String?,
    val user: UnsplashUser,
    val urls: UnspalashPhotoUrls

) : Parcelable {
    @Parcelize
    data class UnspalashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable

    @Parcelize
    data class UnsplashUser(
        val name: String,
        val username: String,
        val profile_image: profileImage
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }

    @Parcelize
    data class profileImage(
        val small: String,
        val medium: String,
        val large: String
    ) : Parcelable


}
