package app.meedu.crypto.presentation.modules.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.meedu.crypto.core.*
import app.meedu.crypto.domain.models.*
import app.meedu.crypto.presentation.*
import kotlinx.coroutines.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen() {
  val exchangeRepository = LocalExchangeRepository.current
  val state = remember {
    mutableStateListOf<Crypto>()
  }
  val items = state.toList()

  LaunchedEffect(
    key1 = "Dashboard",
    block = {
      CoroutineScope(Dispatchers.IO).launch {
        when (val result = exchangeRepository.getCryptoPrices(ids = "bitcoin,ethereum,monero")) {
          is Either.Right -> state.addAll(result.value)
          else -> {}
        }
      }
    },
  )

  Scaffold(topBar = {
    TopAppBar {
      val navController = LocalNavController.current
      Button(
        onClick = {
          navController.navigate(Screens.Profile.route)
        },
      ) {
        Text(text = "Profile")
      }
    }
  }) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center,
    ) {
      if (items.isEmpty()) {
        CircularProgressIndicator()
      } else {
        Column {
          items.forEach {
            Text(text = it.name)
          }
        }
      }
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