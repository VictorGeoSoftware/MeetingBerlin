package com.victor.test.meetingberlin.presenters

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */
abstract class Presenter<T1> {
    var view: T1? = null


    open fun destroy() {
        System.out.println("check onDestroy!")
    }
}