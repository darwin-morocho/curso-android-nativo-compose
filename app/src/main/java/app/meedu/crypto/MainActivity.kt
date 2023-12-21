package app.meedu.crypto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.meedu.crypto.presentation.LocalNavController
import app.meedu.crypto.presentation.Navigator
import app.meedu.crypto.presentation.Screens
import app.meedu.crypto.presentation.modules.dashboard.DashboardScreen
import app.meedu.crypto.presentation.modules.profile.ProfileScreen
import app.meedu.crypto.presentation.modules.sign_in.SignInScreen
import app.meedu.crypto.presentation.modules.splash.SplashScreen

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Navigator {
        NavHost(
          navController = LocalNavController.current,
          startDestination = Screens.Dashboard.route
        ) {
          composable(Screens.Splash.route) {
            SplashScreen()
          }
          composable(Screens.Dashboard.route) {
            DashboardScreen()
          }
          composable(Screens.Profile.route) {
            ProfileScreen()
          }
          composable(Screens.SignIn.route) {
            SignInScreen()
          }
        }
      }
    }
  }
}