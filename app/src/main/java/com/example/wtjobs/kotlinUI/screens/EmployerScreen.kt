package com.example.wtjobs.kotlinUI.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wtjobs.kotlinUI.EmployerPageViewModel
import com.example.wtjobs.kotlinUI.components.AcceptedApplicationCard
import com.example.wtjobs.kotlinUI.components.ApplicationCard

@Composable
fun EmployerScreen(viewModel: EmployerPageViewModel = viewModel())
{

    val acceptedApplications by viewModel.acceptedApplications.collectAsState()
    val rejectedApplications by viewModel.rejectedApplications.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Reviewed applications",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .padding(bottom = 16.dp))



        Row (
            modifier = Modifier
                .size(400.dp,40.dp)
                .background(Color.LightGray)
        ){

            Text(text = "Accepted applications")
        }

        Row (
            modifier = Modifier
                .background(Color.DarkGray)
                .padding(20.dp)
                .size(400.dp,200.dp)
        ){

            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ){
                items(acceptedApplications) { application ->
                    AcceptedApplicationCard(application)
                }
            }


        }

        Spacer(modifier = Modifier.height(40.dp))

        Row (
            modifier = Modifier
                .size(400.dp,40.dp)
                .background(Color.LightGray)

        ){

            Text(text = "Rejected applications")
        }

        Row (
            modifier = Modifier
                .background(Color.DarkGray)
                .padding(20.dp)
                .size(400.dp,200.dp)
        ){

            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ){
                items(rejectedApplications) { application ->
                    AcceptedApplicationCard(application)
                }
            }



        }



    }


}
