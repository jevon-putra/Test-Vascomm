package com.jop.testvascomm.ui.component

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.ContentViewCallback
import com.jop.testvascomm.R

class CustomSnackbarView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    var tvMsg: TextView
    var ivIcon: ImageView
    var layRoot: CardView

    init {
        View.inflate(context, R.layout.view_snackbar_layout, this)
        clipToPadding = false
        this.tvMsg = findViewById(R.id.tv_message)
        this.ivIcon = findViewById(R.id.iv_icon)
        this.layRoot = findViewById(R.id.snack_card)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        val scaleX = ObjectAnimator.ofFloat(ivIcon, View.SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(ivIcon, View.SCALE_Y, 0f, 1f)
        val animatorSet = AnimatorSet().apply {
            interpolator = OvershootInterpolator()
            setDuration(500)
            playTogether(scaleX, scaleY)
        }
        animatorSet.start()
    }

    override fun animateContentOut(delay: Int, duration: Int) {

    }
}