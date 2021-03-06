package com.example.common.buttons.transitionButton

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.WindowManager

object WindowUtils {

    fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    fun makeStatusbarTransparent(activity: Activity) {

        setWindowFlag(
            activity,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            false
        )
        activity.window.statusBarColor = Color.TRANSPARENT
    }

    fun getWidth(activity: Activity): Int {
        return activity.resources.displayMetrics.widthPixels
    }

    fun getHeight(activity: Activity): Int {
        return activity.resources.displayMetrics.heightPixels
    }

    fun getWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun getHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }
}