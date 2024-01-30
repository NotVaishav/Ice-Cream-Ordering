package com.example.ice_cream_app

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ice_cream_app.data.IceCreamUIState
import com.example.ice_cream_app.ui.IceCreamViewModel
import com.example.ice_cream_app.ui.theme.IceCreamAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IceCreamScreen(
    modifier: Modifier = Modifier,
    iceCreamViewModel: IceCreamViewModel = viewModel(),
) {
    val iceCreamUiState by iceCreamViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            IceCreamTopAppBar()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            IceCreamTypeField(iceCreamUiState, iceCreamViewModel, focusManager, context)
            Image(painter = painterResource(id = R.drawable.ice_cream), contentDescription = null)
            IceCreamQuantity(iceCreamUiState, iceCreamViewModel, context)
            IceCreamPrice(iceCreamUiState)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IceCreamTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "IceCream Order",
                color = Color.White,
                style = MaterialTheme.typography.displaySmall
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IceCreamTypeField(
    iceCreamUiState: IceCreamUIState,
    iceCreamViewModel: IceCreamViewModel,
    focusManager: FocusManager,
    context: Context,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Type:", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = iceCreamUiState.type,
            onValueChange = {
                iceCreamViewModel.setType(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done // Set the keyboard action to "Done"
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    iceCreamViewModel.checkType()
                    focusManager.clearFocus()
                }
            ),
            label = { Text("Cone or Cup") },
            textStyle = TextStyle(fontSize = 20.sp)
        )
    }
    LaunchedEffect(iceCreamUiState.isCorrectType) {
        if (!iceCreamUiState.isCorrectType) {
            Toast.makeText(context, "Invalid ice-cream type", Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IceCreamQuantity(
    iceCreamUiState: IceCreamUIState,
    iceCreamViewModel: IceCreamViewModel,
    context: Context,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = {
            if (!iceCreamUiState.isCorrectType) {
                Toast.makeText(context, "Invalid Ice-cream Type", Toast.LENGTH_SHORT).show()
            } else {
                iceCreamViewModel.decreaseQuantity()
            }
        }) {
            Text(text = "-")
        }
        OutlinedTextField(
//            value = TextFieldValue(
//                text = iceCreamUiState.quantity.toString(),
//                selection = TextRange(iceCreamUiState.quantity.toString().length)
//            ),
            value = iceCreamUiState.quantity.toString(),
            onValueChange = {
                val newValue = it.toIntOrNull() ?: 0
                val clampedValue = newValue.coerceIn(-999999, 999999)
                if (!iceCreamUiState.isCorrectType) {
                    Toast.makeText(context, "Invalid Ice-cream Type", Toast.LENGTH_SHORT).show()
                } else if (newValue < 0) {
                    Toast.makeText(context, "Invalid Quantity", Toast.LENGTH_SHORT).show()
                } else {
                    iceCreamViewModel.setQuantity(clampedValue)
                }
            },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            textStyle = TextStyle(fontSize = 20.sp),
            modifier = Modifier.width(200.dp)
        )
        Button(onClick = {
            if (!iceCreamUiState.isCorrectType) {
                Toast.makeText(context, "Invalid Ice-cream Type", Toast.LENGTH_SHORT).show()
            } else {
                iceCreamViewModel.increaseQuantity()
            }
        }) {
            Text(text = "+")
        }
    }
}

@Composable
fun IceCreamPrice(iceCreamUiState: IceCreamUIState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Total Price : ", style = MaterialTheme.typography.headlineMedium)
        Text(
            text = "$${iceCreamUiState.totalPrice}",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IceCreamScreenPreview() {
    IceCreamAppTheme {
        IceCreamScreen()
    }
}