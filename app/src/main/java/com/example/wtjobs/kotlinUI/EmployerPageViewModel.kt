package com.example.wtjobs.kotlinUI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wtjobs.kotlinData.Application
import com.google.firebase.auth.FirebaseAuth
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

    private val _acceptedApplications = MutableStateFlow<List<Application>>(emptyList())
    val acceptedApplications: StateFlow<List<Application>> = _acceptedApplications.asStateFlow()

    private val _rejectedApplications = MutableStateFlow<List<Application>>(emptyList())
    val rejectedApplications: StateFlow<List<Application>> = _rejectedApplications.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val databaseReference = FirebaseDatabase.getInstance().getReference("applications")
    val applicationsList = mutableListOf<Application>()
    val rejectedApplicationList = mutableListOf<Application>()
    val acceptedApplicationList = mutableListOf<Application>()


    private val _employerEmail = MutableStateFlow<String?>(null)
    val employerEmail: StateFlow<String?> = _employerEmail.asStateFlow()
    init {
       val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _employerEmail.value = currentUser.email
        }
        else
        {
            auth.addAuthStateListener{firebaseAuth ->
                val user = firebaseAuth.currentUser
                _employerEmail.value = user?.email

            }

        }

    }




    fun fetchApplications(){

        viewModelScope.launch {
            _isLoading.value=true
            try {
                databaseReference.addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        val pendingApplicationsList = mutableListOf<Application>()


                        for (applicantSnapshot in snapshot.children) {
                            for (applicationSnapshot in applicantSnapshot.children) {
                                val application = applicationSnapshot.getValue(Application::class.java)
                                application?.let {
                                    if(application.jobApplicationStatus=="pending")
                                    {
                                        pendingApplicationsList.add(it)
                                    }
                                    else if (application.jobApplicationStatus=="rejected")
                                    {
                                        rejectedApplicationList.add(it)

                                    }
                                    else if (application.jobApplicationStatus=="accepted")
                                    {
                                        acceptedApplicationList.add(it)
                                    }
                                    else
                                    {
                                        _error.value="Unknown state of application"
                                    }

                                     }
                            }

                        }
                        _applications.value = pendingApplicationsList.toList()
                        _acceptedApplications.value=acceptedApplicationList.toList()
                        _rejectedApplications.value=rejectedApplicationList.toList()
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

    fun rejectApplication(application: Application) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (applicantSnapshot in snapshot.children) {
                            for (applicationSnapshot in applicantSnapshot.children) {
                                val existingApplication = applicationSnapshot.getValue(Application::class.java)

                                if (existingApplication?.userGmail == application.userGmail && existingApplication.jobTitlee == application.jobTitlee && existingApplication.jobLocationn == application.jobLocationn) {
                                    applicationSnapshot.ref.child("jobApplicationStatus").setValue("rejected").addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            applicationsList.remove(application)
                                            _rejectedApplications.value = rejectedApplicationList.toList()
                                            _isLoading.value = false
                                        } else {
                                            _error.value = task.exception?.message
                                            _isLoading.value = false
                                        }
                                    }
                                    break
                                }
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        _error.value = databaseError.message
                        _isLoading.value = false
                    }
                })
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun acceptApplication(application: Application){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {



                        for (applicantSnapshot in snapshot.children) {
                            for (applicationSnapshot in applicantSnapshot.children) {
                                val existingApplication = applicationSnapshot.getValue(Application::class.java)

                                if (existingApplication?.userGmail == application.userGmail && existingApplication.jobTitlee == application.jobTitlee && existingApplication.jobLocationn == application.jobLocationn) {
                                    applicationSnapshot.ref.child("jobApplicationStatus").setValue("accepted").addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            applicationsList.remove(application)
                                            _acceptedApplications.value = acceptedApplicationList.toList()
                                            _isLoading.value = false

                                        } else {
                                            _error.value = task.exception?.message
                                            _isLoading.value = false
                                        }
                                    }
                                    break
                                }
                            }
                        }

                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        _error.value = databaseError.message
                        _isLoading.value = false
                    }
                })
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
    }

}}

