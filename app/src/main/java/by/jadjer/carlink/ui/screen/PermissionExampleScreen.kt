package by.jadjer.carlink.ui.screen

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat

@Composable
fun PermissionExampleScreen() {
    val context = LocalContext.current

    // 1. Создаем лаунчер для запроса разрешения
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Разрешение получено
            println("Permission Granted")
        } else {
            // В разрешении отказано
            println("Permission Denied")
        }
    }

    Button(onClick = {
        val permission = Manifest.permission.CAMERA

        when {
            // Проверяем, есть ли уже разрешение
            ContextCompat.checkSelfPermission(context, permission) ==
                    PackageManager.PERMISSION_GRANTED -> {
                // Уже разрешено
            }

            // Нужно ли показать пояснение
            (context as Activity).shouldShowRequestPermissionRationale(permission) -> {
                // Здесь показать свой AlertDialog с пояснением
            }

            else -> {
                // Первый запрос или "Больше не спрашивать"
                permissionLauncher.launch(permission)
            }
        }
    }) {
        Text("Запросить разрешение")
    }
}

@Preview(showBackground = true)
@Composable
fun PermissionExampleScreenPreview() {
    PermissionExampleScreen()
}
