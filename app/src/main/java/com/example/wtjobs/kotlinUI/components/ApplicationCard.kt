package com.example.wtjobs.kotlinUI.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wtjobs.kotlinData.Application
import com.example.wtjobs.kotlinUI.EmployerPageViewModel


@Composable
fun ApplicationCard(application: Application)
{

    val viewModel: EmployerPageViewModel = viewModel()
    var showRejectDialog by rememberSaveable { mutableStateOf(false) }
    var showAcceptDialog by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalAlignment = Alignment.Top
        )
        {



            Column(modifier = Modifier.weight(1f))
            {
                Text(
                    text = "Applicant email: ${application.userGmail}",
                    color = Color.Black
                )
                Text(
                    text = "Location : ${application.jobLocationn}"
                )
                Text(
                    text = "Applied for : ${application.jobTitlee}"

                )
                Text(
                    text = "Status of application: ${application.jobApplicationStatus}",
                    color = when (application.jobApplicationStatus) {
                        "Accepted" -> Color.Green
                        "Rejected" -> Color.Red
                        else -> Color.Black
                    }

                )

            }

            Spacer(modifier = Modifier.width(16.dp))



            Column {
                Button(

                    onClick = { showAcceptDialog=true },
                    colors = ButtonDefaults.buttonColors(Color.Green)
                ) {
                    Text(text = "Accept",
                        color = Color.White)
                }
                Button(
                    onClick = { showRejectDialog=true },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(text = "Reject",
                        color = Color.White)
                }
            }
        }
    }

    if (showRejectDialog) {
        AlertDialog(
            onDismissRequest = { showRejectDialog = false },
            title = { Text("Delete Application") },
            text = { Text("Are you sure you want to delete this application?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.rejectApplication(application)
                    showRejectDialog = false
                })

                {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRejectDialog = false }) {
                    Text("No")
                }
            }
        )
    }

    if (showAcceptDialog) {
        AlertDialog(
            onDismissRequest = { showAcceptDialog = false },
            title = { Text("Accept Application") },
            text = { Text("Are you sure you want to accept this application?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.acceptApplication(application)
                    showAcceptDialog = false
                })

                {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAcceptDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}


