package com.example.ice_cream_app.data

data class IceCreamUIState(
    var quantity: Int = 0,
    val totalPrice: Double = 0.0,
    val isCorrectType: Boolean = true,
    val type: String = "Cone",
    val iceCreamType: IceCreamType = IceCreamItems[0]
)