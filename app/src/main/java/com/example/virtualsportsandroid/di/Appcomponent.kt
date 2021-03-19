@file:Suppress("MatchingDeclarationName")
package com.example.virtualsportsandroid.di

import android.content.Context
import com.example.virtualsportsandroid.login.ui.LoginFragment
import com.example.virtualsportsandroid.login.ui.RegistrationFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun context(): Context

    fun inject(registrationFragment: RegistrationFragment): RegistrationFragment
    fun inject(loginFragment: LoginFragment): LoginFragment

}
