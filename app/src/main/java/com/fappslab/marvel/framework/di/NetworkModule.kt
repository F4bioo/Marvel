package com.fappslab.marvel.framework.di

import com.fappslab.marvel.BuildConfig
import com.fappslab.marvel.framework.network.MarvelApi
import com.fappslab.marvel.framework.network.interceptor.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_SECONDS = 15L

    @Provides
    fun provideInterceptor(
    ): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else HttpLoggingInterceptor.Level.NONE
        )
    }

    @Provides
    fun provideAuthorizationInterceptor(
    ): AuthorizationInterceptor = AuthorizationInterceptor(
        publicKey = BuildConfig.PUBLIC_KEY,
        privateKey = BuildConfig.PRIVATE_KEY,
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    )

    @Provides
    fun provideOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authorizationInterceptor)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideGsonConverterFactory(
    ): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): MarvelApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()
        .create(MarvelApi::class.java)
}
