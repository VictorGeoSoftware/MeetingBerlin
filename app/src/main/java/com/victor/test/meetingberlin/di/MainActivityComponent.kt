package com.victor.test.meetingberlin.di

import com.victor.test.meetingberlin.ui.main.MainActivity
import com.victor.test.meetingberlin.ui.main.MainActivityModule
import dagger.Subcomponent

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */

@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}