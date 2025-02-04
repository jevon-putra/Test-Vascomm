package com.jop.testvascomm.data.model

data class MenuEvent(
    val title: String,
    val message: String,
    val isButton: Boolean,
    val isIllustrationInLeft: Boolean,
    val background: Int,
    val textButton: String,
    val illustration: Int
)
