package com.example.wtjobs.kotlinUI.components


import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wtjobs.kotlinUI.navigation.BottomNavItem

@Composable
fun BottomNavigationBar(navController: NavHostController)
{
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Applications,
        BottomNavItem.MyAccount
    )


    BottomNavigation{
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach(){
                item->BottomNavigationItem(
            icon = { Icon(item.icon, contentDescription = item.title) },
            label = { Text(item.title) },

            selected = currentRoute == item.route,
            onClick = {
                navController.navigate(item.route){
                    popUpTo(navController.graph.startDestinationId){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }


        )
        }
    }


}