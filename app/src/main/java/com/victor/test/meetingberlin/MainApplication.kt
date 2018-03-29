package com.victor.test.meetingberlin

import android.app.Application
import com.victor.test.meetingberlin.di.AppComponent
import com.victor.test.meetingberlin.di.AppModule
import com.victor.test.meetingberlin.di.DaggerAppComponent

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */
class MainApplication: Application() {

    val component: AppComponent by lazy { DaggerAppComponent.builder().appModule(AppModule(this)).build() }

    override fun onCreate() {
        super.onCreate()

        component.inject(this)
    }
}