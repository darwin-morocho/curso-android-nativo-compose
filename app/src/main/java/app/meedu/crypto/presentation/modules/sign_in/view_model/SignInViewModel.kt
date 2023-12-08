package app.meedu.crypto.presentation.modules.sign_in.view_model

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

val LocalSignInViewModel = compositionLocalOf<SignInViewModel> {
    error("SignInViewModel not found")
}

class SignInViewModel(private val initState: SignInState) : ViewModel() {

    val _state = mutableStateOf<SignInState>(initState)

    val state: SignInState get() = _state.value


    fun onEmailChanged(email: String) {
        _state.value = state.copyWith(email = email)
    }

    fun onPasswordChanged(password: String) {
        _state.value = state.copyWith(password = password)
    }
}