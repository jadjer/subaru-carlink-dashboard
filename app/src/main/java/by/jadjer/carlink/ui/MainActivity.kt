package by.jadjer.carlink.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import by.jadjer.carlink.service.CarlinkForegroundService
import by.jadjer.carlink.ui.theme.CarLinkDashboardTheme
import by.jadjer.carlink.CarlinkApplication

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val appContainer = (application as CarlinkApplication).container

        setContent {
            CarLinkDashboardTheme {
//                MainScreen()
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, CarlinkForegroundService::class.java)
                        startForegroundService(intent)
                    }) {
                        Text("Запустить мониторинг USB")
                    }
                }
            }
        }
    }
}
