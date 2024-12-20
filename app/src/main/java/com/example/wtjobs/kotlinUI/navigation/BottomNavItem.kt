package com.example.wtjobs.kotlinUI.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String) {
     object Home : BottomNavItem("Home", Icons.Default.Home,"home")
     object Applications: BottomNavItem("Applications", Icons.Default.Add,"applications")
     object MyAccount: BottomNavItem("MyAccount", Icons.Default.Person,"myaccount")

}