package by.jadjer.carlink.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import by.jadjer.carlink.ui.screen.AutoUpdateScreen
import by.jadjer.carlink.ui.screen.MainScreen
import by.jadjer.carlink.ui.theme.CarLinkDashboardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarLinkDashboardTheme {
//                MainScreen()
                AutoUpdateScreen()
            }
        }
    }
}
