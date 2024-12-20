package com.example.wtjobs.kotlinData

data class Application(
    val jobLocationn: String = "",
    val jobTitlee: String = "",
    var jobApplicationStatus: String = "",
    val userGmail: String=""
) {
    constructor() : this("", "", "", "")
}





