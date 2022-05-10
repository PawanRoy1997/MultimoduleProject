package com.example.common.models.utils

import android.graphics.Color

class ColorUtils {
    companion object {
        private fun correct(color: String): String {
            return color.replace(
                "#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])".toRegex(),
                "#$1$1$2$2$3$3"
            )
        }

        fun parse(color: String): Int {
            val result: String = when (color.length) {
                4 -> color.replace("#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])", "#$1$1$2$2$3$3")
                5 -> color.replace(
                    "#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])",
                    "#$2$2$3$3$4$4"
                )
                else -> "#000"
            }
            return Color.parseColor(result)
        }
    }
}