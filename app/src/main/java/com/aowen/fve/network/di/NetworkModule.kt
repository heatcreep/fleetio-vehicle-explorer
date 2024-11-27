package com.aowen.fve.network.di

import androidx.compose.ui.util.trace
import com.aowen.fve.BuildConfig
import com.aowen.fve.network.FveNetworkDatasource
import com.aowen.fve.network.retrofit.RetrofitFleetioNetwork
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.android.AndroidLogHandler.setLevel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = trace("FveOkHttpClient") {
        OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("Authorization", BuildConfig.AUTH_TOKEN)
                    builder.header("Account-Token", BuildConfig.ACCOUNT_TOKEN)
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }
            .build()
    }

    @Provides
    @Singleton
    fun providesFleetioNetwork(
        networkJson: Json,
        okhttpCallFactory: dagger.Lazy<Call.Factory>,
    ): FveNetworkDatasource = RetrofitFleetioNetwork(networkJson, okhttpCallFactory)
}