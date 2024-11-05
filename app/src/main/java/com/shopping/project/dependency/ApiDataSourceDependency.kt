package com.shopping.project.dependency

import com.shopping.project.Utils.RequestInterceptor
import com.shopping.project.Utils.UnsafeOkHttpClient
import com.shopping.project.data.api.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.shopping.project.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

fun provideGson(): Gson {
    val builder = GsonBuilder()

    builder.registerTypeAdapter(Date::class.java, JsonDeserializer<Date> { json, _, _ ->
        json?.asJsonPrimitive?.asLong?.let {
            return@JsonDeserializer Date(it)
        }
    })

    builder.registerTypeAdapter(Date::class.java, JsonSerializer<Date> { date, _, _ ->
        JsonPrimitive(date.time)
    })

    return builder.create()
}

fun provideRequestInterceptor(): RequestInterceptor {
    return RequestInterceptor()
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logInterceptor = HttpLoggingInterceptor()
    logInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return logInterceptor
}

fun provideOkHttpClient(requestInterceptor: RequestInterceptor,
                        logInterceptor: HttpLoggingInterceptor): OkHttpClient {

    val builder = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    //Adiciona os interceptors
    builder.addInterceptor(logInterceptor)
    builder.addInterceptor(requestInterceptor)

    builder.connectTimeout(2, TimeUnit.MINUTES)
    builder.readTimeout(1, TimeUnit.MINUTES)
    builder.readTimeout(1, TimeUnit.MINUTES)

    return builder.build()
}

fun provideApiDataSource(okHttpClient: OkHttpClient, gson: Gson): ApiService {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(ApiService::class.java)
}