package com.example.ice_cream_app.data

import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.example.ice_cream_app.R

data class IceCreamType(
    @StringRes val type: Int,
    val price: Double
)

val IceCreamItems = listOf(
    IceCreamType(R.string.cone, 3.69),
    IceCreamType(R.string.cup, 3.39),
)
