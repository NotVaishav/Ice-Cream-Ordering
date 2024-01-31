package com.example.ice_cream_app.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import com.example.ice_cream_app.data.IceCreamItems
import com.example.ice_cream_app.data.IceCreamType
import com.example.ice_cream_app.data.IceCreamUIState
import com.example.ice_cream_app.data.iceCreamFlavours
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class IceCreamViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(IceCreamUIState())
    val uiState: StateFlow<IceCreamUIState> = _uiState.asStateFlow()

    fun setType(type: String, checkType: Boolean = false) {
        Log.d("settype", type)
        _uiState.update { currentState ->
            currentState.copy(
                type = type,
                isCorrectType = true
            )
        }
        if (checkType) {
            checkType()
        }
    }

    fun checkType() {
        val currentType = _uiState.value.type
        if (currentType.lowercase() == "cone") {
            _uiState.update { currentState ->
                currentState.copy(
                    iceCreamType = IceCreamItems[0],
                    totalPrice = calculatePrice(currentState.quantity, IceCreamItems[0])
                )
            }

        } else if (currentType.lowercase() == "cup") {
            _uiState.update { currentState ->
                currentState.copy(
                    iceCreamType = IceCreamItems[1],
                    totalPrice = calculatePrice(currentState.quantity, IceCreamItems[1])
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
                    totalPrice = calculatePrice(quantity)
                )
            } else {
                currentState
            }
        }
    }

    private fun calculatePrice(
        currentQuantity: Int = _uiState.value.quantity,
        currentType: IceCreamType = _uiState.value.iceCreamType,
        currentIndex: Int = _uiState.value.selectedFlavourIndex
    ): Double {
        val flavourPrice = iceCreamFlavours[currentIndex].price
        val totalPrice = (currentType.price + flavourPrice) * currentQuantity
        return String.format(Locale.US, "%.2f", totalPrice).toDouble()
    }

    fun increaseQuantity() {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = currentState.quantity + 1,
                totalPrice = calculatePrice(currentState.quantity + 1)
            )
        }
    }

    fun decreaseQuantity() {
        _uiState.update { currentState ->
            if (currentState.quantity > 0) {
                currentState.copy(
                    quantity = currentState.quantity - 1,
                    totalPrice = calculatePrice(currentState.quantity - 1)
                )
            } else {
                currentState
            }
        }
    }

    fun switchExpanded() {
        _uiState.update { currentState -> currentState.copy(expanded = !_uiState.value.expanded) }
    }

    fun disableExpanded() {
        _uiState.update { currentState -> currentState.copy(expanded = false) }
    }

    fun updateFlavourIndex(index: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedFlavourIndex = index,
                totalPrice = calculatePrice(currentIndex = index)
            )
        }
    }
}