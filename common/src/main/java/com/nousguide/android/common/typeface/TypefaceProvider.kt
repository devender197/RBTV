package com.nousguide.android.common.typeface

interface TypefaceProvider {
    fun getMainTypeface(): Int
    fun getRegularTypeface(): Int
    fun getBoldTypeface(): Int
}
