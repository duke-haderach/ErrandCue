package com.example.errandcue.app.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.errandcue.feature.history.HistoryScreen
import com.example.errandcue.feature.home.HomeScreen
import com.example.errandcue.feature.settings.SettingsScreen
import com.example.errandcue.feature.taskedit.TaskEditScreen
@Composable
fun AppNavGraph() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "home") {
        composable("home") { HomeScreen(onAddTask = { nav.navigate("create") }, onSettings = { nav.navigate("settings") }, onHistory = { nav.navigate("history") }) }
        composable("create") { TaskEditScreen(onBack = { nav.popBackStack() }) }
        composable("history") { HistoryScreen(onBack = { nav.popBackStack() }) }
        composable("settings") { SettingsScreen(onBack = { nav.popBackStack() }) }
    }
}