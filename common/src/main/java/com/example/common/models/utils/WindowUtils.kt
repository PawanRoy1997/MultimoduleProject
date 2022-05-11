package com.example.common.models.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.WindowManager


object WindowUtils {
    private lateinit var displayMetrics: DisplayMetrics

    private fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    fun makeStatusBarTransparent(activity: Activity) {
        this.setWindowFlag(
            activity,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            false
        );
        activity.window.statusBarColor = Color.TRANSPARENT;
    }

    fun getWidth(activity: Activity): Int {
        setupDisplayMetrics(activity)
        return displayMetrics.widthPixels
    }

    fun getHeight(activity: Activity): Int {
        setupDisplayMetrics(activity)
        return displayMetrics.heightPixels
    }

    private fun setupDisplayMetrics(context: Context) {
        if (!this::displayMetrics.isInitialized) {
            displayMetrics = DisplayMetrics()
        }
        displayMetrics = context.resources.displayMetrics
    }
}