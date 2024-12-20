package com.example.wtjobs.kotlinUI.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wtjobs.kotlinUI.EmployerPageViewModel
import com.example.wtjobs.kotlinUI.screens.EmployerScreen
import com.example.wtjobs.kotlinUI.screens.HomeScreen
import com.example.wtjobs.kotlinUI.screens.MyAccountScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier, viewModel: EmployerPageViewModel)
{

    NavHost(navController=navController, startDestination= BottomNavItem.Home.route)
    {
        composable(BottomNavItem.Home.route) { HomeScreen(viewModel) }

        composable(BottomNavItem.Applications.route) { EmployerScreen(viewModel) }

        composable(BottomNavItem.MyAccount.route) { MyAccountScreen(viewModel) }
    }
}