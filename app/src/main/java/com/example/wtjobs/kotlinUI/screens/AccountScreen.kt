package com.example.wtjobs.kotlinUI.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wtjobs.LoginAndRegistration.Login
import com.example.wtjobs.kotlinUI.EmployerPageViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MyAccountScreen(viewModel: EmployerPageViewModel = viewModel())
{

    val employerInfo by viewModel.employerEmail.collectAsState()
    var navController : NavController = rememberNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White))
    {
        Row (
            modifier = Modifier
                .size(400.dp,500.dp)
                .background(Color.LightGray)
                .padding(16.dp)
        ){
         Column(
             modifier = Modifier
                 .fillMaxSize()
                 .padding(16.dp)
                 .background(Color.DarkGray)
         ) {
             Text(
                 style = MaterialTheme.typography.titleLarge,
                 fontFamily = FontFamily.Serif,
                 text = "My account information",
                 color = Color.White)
             Spacer(modifier = Modifier.height(16.dp))

             Text(
                 style = MaterialTheme.typography.titleLarge,
                 fontFamily = FontFamily.Serif,
                 text = "Email: ${employerInfo ?: "Loading..."} ",
                 color = Color.White

             )
             Spacer(modifier = Modifier.height(36.dp))

             Button(
                 onClick = {

                     FirebaseAuth.getInstance().signOut()
                     navController.navigate(route="Login")

                           },
                 colors = ButtonDefaults.buttonColors(
                     containerColor = Color.Red,
                     contentColor = Color.White
                 )
             ) {
                 Text(text = "Sign out")
             }


         }

        }

    }



}

