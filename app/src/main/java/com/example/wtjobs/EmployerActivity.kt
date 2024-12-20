package com.example.wtjobs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.wtjobs.kotlinUI.EmployerPageViewModel
import com.example.wtjobs.kotlinUI.screens.EmployerPage
import com.google.firebase.FirebaseApp

class EmployerActivity : ComponentActivity() {

    private val employerPageViewModel: EmployerPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
                EmployerPage(employerPageViewModel)
        }
    }





}