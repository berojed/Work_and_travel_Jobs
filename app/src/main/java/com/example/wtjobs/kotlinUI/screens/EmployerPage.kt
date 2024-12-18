package com.example.wtjobs.kotlinUI.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.wtjobs.kotlinUI.EmployerPageViewModel
import com.example.wtjobs.kotlinUI.components.BottomNavigationBar
import com.example.wtjobs.kotlinUI.components.TopBar
import com.example.wtjobs.kotlinUI.navigation.NavigationGraph


@Composable
fun EmployerPage(viewModel: EmployerPageViewModel = viewModel())
{

    val navController = rememberNavController()
   Scaffold(
       topBar = { TopBar(onLogoutClick = { /*TODO*/ }) },
       bottomBar = {BottomNavigationBar(navController)}
   ) {
       padding->
       NavigationGraph(navController,Modifier.padding(padding),viewModel)
   }

}








