package com.jop.testvascomm.utils

import android.content.res.Resources

object SizeUtil {
    fun pxFromDp(dp: Float): Int {
        return (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    }
}