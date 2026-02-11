package by.jadjer.carlink.ui.screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import by.jadjer.carlink.service.CarlinkForegroundService
import by.jadjer.carlink.ui.viewmodel.ServiceViewModel

@Composable
fun AutoUpdateScreen(viewModel: ServiceViewModel = viewModel()) {
    val context = LocalContext.current
    val currentCount by viewModel.uiState.collectAsStateWithLifecycle()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        if (results.all { it.value }) {
            startAndBindService(context, viewModel.connection)
        }
    }

    LaunchedEffect(Unit) {
        val permissions = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        val allGranted = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if (allGranted) {
            startAndBindService(context, viewModel.connection)
        } else {
            permissionLauncher.launch(permissions.toTypedArray())
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                context.unbindService(viewModel.connection)
            } catch (e: Exception) {
            }
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Счетчик из сервиса: $currentCount",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

private fun startAndBindService(context: Context, connection: ServiceConnection) {
    val intent = Intent(context, CarlinkForegroundService::class.java)
    context.startForegroundService(intent)
    context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
}
