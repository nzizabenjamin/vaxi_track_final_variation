package com.beeqan.vaxi_track

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.beeqan.vaxi_track.ui.screens.*
import com.beeqan.vaxi_track.ui.theme.Vaxi_trackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Vaxi_trackTheme {
                VaxiTrackApp()
            }
        }
    }
}

sealed class Screen {
    object Login : Screen()
    object RoleSelection : Screen()
    object ParentDashboard : Screen()
    data class VaccinationTimeline(val child: ChildInfo) : Screen()
    object CHWDashboard : Screen()
    object Notifications : Screen()
    object RegisterChild : Screen()
}

@Composable
fun VaxiTrackApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    when (val screen = currentScreen) {
        is Screen.Login -> {
            LoginScreen(
                onNavigateToRoleSelection = {
                    currentScreen = Screen.RoleSelection
                },
                onNavigateToRegister = {
                    // TODO: Navigate to registration
                }
            )
        }
        is Screen.RoleSelection -> {
            RoleSelectionScreen(
                onNavigateToParentDashboard = {
                    currentScreen = Screen.ParentDashboard
                },
                onNavigateToCHWDashboard = {
                    currentScreen = Screen.CHWDashboard
                }
            )
        }
        is Screen.ParentDashboard -> {
            ParentDashboard(
                onNavigateToTimeline = { child ->
                    currentScreen = Screen.VaccinationTimeline(child)
                },
                onNavigateToNotifications = {
                    currentScreen = Screen.Notifications
                },
                onNavigateToRegisterChild = {
                    currentScreen = Screen.RegisterChild
                }
            )
        }
        is Screen.VaccinationTimeline -> {
            VaccinationTimeline(
                child = screen.child,
                onNavigateBack = {
                    currentScreen = Screen.ParentDashboard
                }
            )
        }
        is Screen.CHWDashboard -> {
            CHWDashboard(
                onNavigateToRecordVaccination = {
                    // TODO: Implement record vaccination screen
                },
                onNavigateToRegisterChild = {
                    currentScreen = Screen.RegisterChild
                }
            )
        }
        is Screen.Notifications -> {
            NotificationsList(
                onNavigateBack = {
                    currentScreen = Screen.ParentDashboard
                }
            )
        }
        is Screen.RegisterChild -> {
            RegisterChildForm(
                onNavigateBack = {
                    currentScreen = Screen.ParentDashboard
                }
            )
        }
    }
}