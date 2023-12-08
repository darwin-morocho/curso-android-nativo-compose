package app.meedu.crypto.presentation.modules.sign_in

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.meedu.crypto.presentation.LocalNavController
import app.meedu.crypto.presentation.Navigator
import app.meedu.crypto.presentation.global.view_models.session.LocalSessionViewModel
import app.meedu.crypto.presentation.modules.sign_in.view_model.LocalSignInViewModel
import app.meedu.crypto.presentation.modules.sign_in.view_model.SignInState
import app.meedu.crypto.presentation.modules.sign_in.view_model.SignInViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignInScreen() {

    CompositionLocalProvider(
        LocalSignInViewModel provides SignInViewModel(initState = SignInState())
    ) {
        Scaffold {
            Column {
                val signInViewModel = LocalSignInViewModel.current
                val state = signInViewModel.state
                TextField(
                    modifier = Modifier.padding(vertical = 20.dp),
                    value = state.email,
                    onValueChange = {
                        signInViewModel.onEmailChanged(it)
                    },
                    label = {
                        Text(text = "Email")
                    },
                )
                TextField(
                    modifier = Modifier.padding(vertical = 20.dp),
                    value = state.password,
                    onValueChange = {
                        signInViewModel.onPasswordChanged(it)
                    },
                    label = {
                        Text(text = "Password")
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Navigator {
        SignInScreen()
    }
}