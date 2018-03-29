package com.victor.test.meetingberlin.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.test.meetingberlin.MainApplication

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */

val Activity.app: MainApplication
    get() = application as MainApplication

fun trace(traceToShow: String) {
    System.out.println("MeetingBerlin - Traces || $traceToShow")
}

fun ViewGroup.inflate(layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

fun getDpFromValue(context: Context, value: Int): Int =
        Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), context.resources.displayMetrics))