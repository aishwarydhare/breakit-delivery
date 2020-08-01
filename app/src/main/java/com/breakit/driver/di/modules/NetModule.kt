package com.breakit.driver.di.modules

import com.breakit.driver.BuildConfig
import com.breakit.driver.data.delivery.DeliveryApi
import com.breakit.driver.data.location.LocationApi
import com.breakit.driver.data.login.LoginApi
import com.breakit.driver.network.AuthInterceptorImpl
import com.breakit.driver.network.QueryParamsInterceptor
import com.breakit.driver.network.SessionCheckInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Dagger module for network ops
 */
@Module
class NetModule {

    @Module
    companion object {

        /**
         * Provide logging interceptor
         */
        @JvmStatic
        @Singleton
        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }

        /**
         * Provide OkHttp
         */
        @JvmStatic
        @Singleton
        @Provides
        fun provideOkHttp(
            loggingInterceptor: HttpLoggingInterceptor,
            authInterceptor: AuthInterceptorImpl,
            queryParamsInterceptor: QueryParamsInterceptor,
            sessionCheckInterceptor: SessionCheckInterceptor
        ): OkHttpClient =
            OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addNetworkInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(queryParamsInterceptor)
                .addInterceptor(sessionCheckInterceptor)
                .build()

        /**
         * Provide Retrofit
         */
        @Singleton
        @JvmStatic
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API_URL)
                .addConverterFactory(
                    MoshiConverterFactory.create(
                        Moshi.Builder()
                            .add(KotlinJsonAdapterFactory())
                            .build()
                    )
                )
                .client(okHttpClient)
                .build()

        /**
         * Create [LoginApi]
         */
        @JvmStatic
        @Provides
        fun provideLoginApi(retrofit: Retrofit): LoginApi = LoginApi.create(retrofit)

        /**
         * Create [DeliveryApi]
         */
        @JvmStatic
        @Provides
        fun provideCustomerApi(retrofit: Retrofit): DeliveryApi = DeliveryApi.create(retrofit)


        /**
         * Create [LocationApi]
         */
        @JvmStatic
        @Provides
        fun provideOrderApi(retrofit: Retrofit): LocationApi = LocationApi.create(retrofit)

    }
}
