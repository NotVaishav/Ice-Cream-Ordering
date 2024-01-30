package com.example.ice_cream_app.ui

import android.widget.Toast
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import com.example.ice_cream_app.data.IceCreamItems
import com.example.ice_cream_app.data.IceCreamType
import com.example.ice_cream_app.data.IceCreamUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class IceCreamViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(IceCreamUIState())
    val uiState: StateFlow<IceCreamUIState> = _uiState.asStateFlow()

    fun setType(type: String) {
        _uiState.update { currentState ->
            currentState.copy(
                type = type,
                isCorrectType = true
            )
        }
    }

    fun checkType() {
        val currentType = _uiState.value.type
        if (currentType.lowercase() == "cone") {
            _uiState.update { currentState ->
                currentState.copy(
                    iceCreamType = IceCreamItems[0],
                    totalPrice = calculatePrice(currentState.quantity, IceCreamItems[0]).toDouble()
                )
            }

        } else if (currentType.lowercase() == "cup") {
            _uiState.update { currentState ->
                currentState.copy(
                    iceCreamType = IceCreamItems[1],
                    totalPrice = calculatePrice(currentState.quantity, IceCreamItems[1]).toDouble()
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isCorrectType = false,
                    totalPrice = 0.0
                )
            }
        }
    }

    fun setQuantity(quantity: Int) {
        _uiState.update { currentState ->
            if (quantity >= 0) {
                currentState.copy(
                    quantity = quantity,
                    totalPrice = calculatePrice(quantity).toDouble()
                )
            } else {
                currentState
            }
        }
    }

    private fun calculatePrice(
        currentQuantity: Int,
        currentType: IceCreamType = _uiState.value.iceCreamType
    ): String {
        val totalPrice = currentType.price * currentQuantity
        return String.format(Locale.US, "%.2f", totalPrice)
    }

    fun increaseQuantity() {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = currentState.quantity + 1,
                totalPrice = calculatePrice(currentState.quantity + 1).toDouble()
            )
        }
    }

    fun decreaseQuantity() {
        _uiState.update { currentState ->
            if (currentState.quantity > 0) {
                currentState.copy(
                    quantity = currentState.quantity - 1,
                    totalPrice = calculatePrice(currentState.quantity - 1).toDouble()
                )
            } else {
                currentState
            }
        }
    }

}