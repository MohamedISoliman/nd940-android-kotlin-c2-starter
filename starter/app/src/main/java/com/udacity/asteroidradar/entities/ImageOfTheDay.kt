package com.udacity.asteroidradar.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ImageOfTheDay(
    @Json(name = "copyright")
    val copyright: String? = "",
    @Json(name = "date")
    val date: String? = "",
    @Json(name = "explanation")
    val explanation: String? = "",
    @Json(name = "hdurl")
    val hdurl: String? = "",
    @Json(name = "media_type")
    val mediaType: String? = "",
    @Json(name = "service_version")
    val serviceVersion: String? = "",
    @Json(name = "title")
    val title: String? = "",
    @Json(name = "url")
    val url: String? = ""
)