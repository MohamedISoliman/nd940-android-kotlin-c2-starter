package com.udacity.asteroidradar.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.entities.ImageOfTheDay
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/**
 *
 * Created by Mohamed Ibrahim on 3/17/20.
 */
interface NasaRemoteApi {
    @GET("neo/rest/v1/feed")
    suspend fun fetchNasaFeed(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): String

    @GET("planetary/apod")
    suspend fun fetchImageOfTheDay(): ImageOfTheDay
}

object RemoteFactory {


    fun createRemote(converter: Converter.Factory): NasaRemoteApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converter)
            .client(makeOkHttpClient())
            .build()

        return retrofit.create(NasaRemoteApi::class.java)

    }

    val moshiConverter by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        MoshiConverterFactory.create(moshi)
    }

    val scalarsConverterFactory = ScalarsConverterFactory.create()

}


private fun makeOkHttpClient(): OkHttpClient {

    val interceptor = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
        .addInterceptor { apiKeyInterceptor(it) }
        .addInterceptor(interceptor).build()
}

private fun apiKeyInterceptor(it: Interceptor.Chain): Response {
    val original: Request = it.request()
    val originalHttpUrl: HttpUrl = original.url

    val url = originalHttpUrl.newBuilder()
        .addQueryParameter("api_key", BuildConfig.NASA_API_KEY)
        .build()
    val requestBuilder: Request.Builder = original.newBuilder()
        .url(url)

    val request: Request = requestBuilder.build()
    return it.proceed(request)
}
