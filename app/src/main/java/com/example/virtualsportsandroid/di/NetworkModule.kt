package com.example.virtualsportsandroid.di

import com.example.virtualsportsandroid.dices.game.data.DiceGameResultService
import com.example.virtualsportsandroid.dices.history.data.DiceGameBetHistoryService
import com.example.virtualsportsandroid.game.data.api.GameScreenService
import com.example.virtualsportsandroid.login.data.api.LoginService
import com.example.virtualsportsandroid.login.data.api.RegistrationService
import com.example.virtualsportsandroid.utils.api.NetworkHeaderInterceptor
import com.example.virtualsportsandroid.utils.apiHost
import com.example.virtualsportsandroid.utils.schema
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(sharedPrefs: SharedPref): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(NetworkHeaderInterceptor(sharedPrefs))
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .baseUrl(HttpUrl.Builder().scheme(schema).host(apiHost).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun provideRegistrationService(retrofit: Retrofit): RegistrationService =
        retrofit.create(RegistrationService::class.java)

    @Singleton
    @Provides
    fun provideGameScreenService(retrofit: Retrofit): GameScreenService =
        retrofit.create(GameScreenService::class.java)

    @Singleton
    @Provides
    fun provideDiceGameResultService(retrofit: Retrofit): DiceGameResultService =
        retrofit.create(DiceGameResultService::class.java)

    @Singleton
    @Provides
    fun provideDiceGameBetHistoryService(retrofit: Retrofit): DiceGameBetHistoryService =
        retrofit.create(DiceGameBetHistoryService::class.java)
}
