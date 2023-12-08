package app.meedu.crypto.presentation.modules.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.meedu.crypto.presentation.LocalNavController
import app.meedu.crypto.presentation.Navigator
import app.meedu.crypto.presentation.Screens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen() {


    Scaffold(
        topBar = {
            TopAppBar {
                val navController = LocalNavController.current
                Button(onClick = {
                    navController.navigate(Screens.Profile.route)
                }) {
                    Text(text = "Profile")
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Dashboard")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Navigator {
        DashboardScreen()
    }
}