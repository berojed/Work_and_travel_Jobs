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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wtjobs.kotlinUI.EmployerPageViewModel
import com.example.wtjobs.kotlinUI.components.ApplicationCard

@Composable
fun HomeScreen(viewModel: EmployerPageViewModel = viewModel())
{

    val applications by viewModel.applications.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchApplications()
    }

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .background(Color.White))
    {
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Applications ",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(bottom = 16.dp))

        Row (modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly)

        {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(Color.Green)
            )
            {
                Text(text = "Sort",
                    color = Color.White)

            }

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(Color.Red)
            )

            {
                Text(text = "Filter",
                    color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        //App list
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize())
        {

            items(applications) { application ->
                ApplicationCard(application)
            }


        }

    }
}