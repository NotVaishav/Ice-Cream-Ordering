package com.example.ice_cream_app.data

import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.example.ice_cream_app.R

data class IceCreamFlavour(
    @StringRes val flavour: Int,
    val price: Double
)

val iceCreamFlavours = listOf(
    IceCreamFlavour(R.string.flavour_1, 0.00),
    IceCreamFlavour(R.string.flavour_2, 1.00),
    IceCreamFlavour(R.string.flavour_3, 2.00),
    IceCreamFlavour(R.string.flavour_4, 4.00),
    IceCreamFlavour(R.string.flavour_5, 5.00),
)

