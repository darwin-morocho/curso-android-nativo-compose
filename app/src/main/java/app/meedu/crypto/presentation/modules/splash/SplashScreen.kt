package app.meedu.crypto.presentation.modules.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.meedu.crypto.presentation.LocalNavController
import app.meedu.crypto.presentation.Navigator
import app.meedu.crypto.presentation.Screens
import app.meedu.crypto.presentation.global.view_models.session.LocalSessionViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SplashScreen() {

    val navController = LocalNavController.current
    val user = LocalSessionViewModel.current.user

    LaunchedEffect(key1 = "splash", block = {
        delay(2000)
        navController.navigate(if (user != null) Screens.Dashboard.route else Screens.SignIn.route)
    })

    Scaffold {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Navigator {
        SplashScreen()
    }
}