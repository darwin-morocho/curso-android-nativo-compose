package app.meedu.crypto.presentation.global.view_models.session

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import app.meedu.crypto.domain.models.User


val LocalSessionViewModel = compositionLocalOf<SessionViewModel> {
    error("SessionViewModel not found")
}


class SessionViewModel(private val initUser: User?) : ViewModel() {
    private val _state = mutableStateOf<User?>(initUser)

    val user: User? get() = _state.value
}