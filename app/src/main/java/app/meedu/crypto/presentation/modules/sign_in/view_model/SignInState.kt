package app.meedu.crypto.presentation.modules.sign_in.view_model

class SignInState(val email: String = "", val password: String = "") {

    fun copyWith(email: String? = null, password: String? = null): SignInState {
        return SignInState(
            email = email ?: this.email,
            password = password ?: this.password
        )
    }
}