package com.victor.test.meetingberlin.di

import com.victor.test.meetingberlin.BuildConfig
import com.victor.test.meetingberlin.network.ReviewsRepository
import com.victor.test.meetingberlin.presenters.reviews.ReviewsPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */

@Module
class NetworkModule {
    companion object {
        const val NAME_BASE_URL = "NAME_BASE_URL"
    }

    @Provides
    @Named(NAME_BASE_URL)
    fun provideBaseUrlString():String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder().readTimeout(BuildConfig.TIME_OUT, TimeUnit.SECONDS).connectTimeout(BuildConfig.TIME_OUT, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(interceptor)

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideGsonConverter(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideCallAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, converter: Converter.Factory, callAdapterFactory: RxJava2CallAdapterFactory, @Named(NAME_BASE_URL) baseUrl:String): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addCallAdapterFactory(callAdapterFactory).addConverterFactory(converter).build()
    }


    // ------------------------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------- WEB SERVICES --------------------------------------------------------------------
    @Provides
    @Singleton
    fun provideReviewsRepository(retrofit: Retrofit) = retrofit.create(ReviewsRepository::class.java)
}