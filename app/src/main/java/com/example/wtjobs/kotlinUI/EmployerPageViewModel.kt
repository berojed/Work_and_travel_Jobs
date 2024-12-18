package com.example.wtjobs.kotlinUI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wtjobs.kotlinData.Application
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmployerPageViewModel:ViewModel() {

    private val _applications = MutableStateFlow<List<Application>>(emptyList())
    val applications: StateFlow<List<Application>> = _applications.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val databaseReference = FirebaseDatabase.getInstance().getReference("applications")
    val applicationsList = mutableListOf<Application>()

    fun fetchApplications(){

        viewModelScope.launch {
            _isLoading.value=true
            try {
                databaseReference.addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        for (applicantSnapshot in snapshot.children) {
                            for (applicationSnapshot in applicantSnapshot.children) {
                                val application = applicationSnapshot.getValue(Application::class.java)
                                application?.let { applicationsList.add(it) }
                            }

                        }
                        _applications.value = applicationsList
                        _isLoading.value = false
                    }

                    override fun onCancelled(error: DatabaseError) {
                        _error.value = error.message
                        _isLoading.value = false
                    }

                })
                }
            catch (e: Exception){
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun rejectApplication(application: Application){

        viewModelScope.launch {
            _isLoading.value=true

            try {
                databaseReference.addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        for (applicantSnapshot in snapshot.children) {
                            for (applicationSnapshot in applicantSnapshot.children) {

                                if (application.userGmail == applicationSnapshot.getValue(Application::class.java)?.userGmail && application.jobApplicationStatus=="u obradi")
                                {
                                    applicationSnapshot.ref.child("jobApplicationStatus").setValue("rejected").addOnCompleteListener {
                                        if(it.isSuccessful)
                                        {
                                            applicationsList.remove(application)
                                            _isLoading.value = false
                                        }
                                        else
                                            _error.value = it.exception?.message

                                    }
                                }





                            }

                        }
                        //_applications.value = applicationsList
                        _isLoading.value = false
                    }

                    override fun onCancelled(error: DatabaseError) {
                        _error.value = error.message
                        _isLoading.value = false
                    }

                })
            }
            catch (e: Exception){
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }




    fun acceptApplication(application: Application){
        viewModelScope.launch {
            _isLoading.value=true

            try {

                application.jobApplicationStatus="Accepted"

            }

            catch (e: Exception){
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

}

