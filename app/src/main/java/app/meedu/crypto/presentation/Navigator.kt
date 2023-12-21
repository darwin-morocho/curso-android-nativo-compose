package app.meedu.crypto.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.meedu.crypto.data.*
import app.meedu.crypto.data.repositories_impl.*
import app.meedu.crypto.domain.repositories.*
import app.meedu.crypto.presentation.global.view_models.session.LocalSessionViewModel
import app.meedu.crypto.presentation.global.view_models.session.SessionViewModel
import okhttp3.*

val LocalNavController = compositionLocalOf<NavHostController> {
  error("NavHostController not found")
}
val LocalExchangeRepository = compositionLocalOf<ExchangeRepository> {
  error("ExchangeRepository not found")
}

@Composable
fun Navigator(content: @Composable () -> Unit) {
  val httpClient = HttpClient(
    okHttpClient = OkHttpClient(),
    baseUrl = "https://api.coincap.io"
  )
  CompositionLocalProvider(
    LocalNavController provides rememberNavController(),
    LocalSessionViewModel provides SessionViewModel(null),
    LocalExchangeRepository provides ExchangeRepositoryImpl(httpClient)
  ) {
    content()
  }
}

sealed class Screens(val route: String) {
  object Splash : Screens(route = "/splash")
  object Dashboard : Screens(route = "/dashboard")
  object Profile : Screens(route = "/profile")
  object SignIn : Screens(route = "/sign-in")
}