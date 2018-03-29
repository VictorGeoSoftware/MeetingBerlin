package com.victor.test.meetingberlin.di

import android.app.Activity
import android.app.Application
import com.victor.test.meetingberlin.ui.main.MainActivityModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, PresenterModule::class])
interface AppComponent {
    fun inject(application: Application)
    fun plus(mainActivityModule: MainActivityModule): MainActivityComponent
}