package com.example.studentmanagementapp

data class Employee(
    var firebaseDocId: String? = null,
    val employeeId: String = "",
    val name: String = "",
    val dob: String = "",
    val gender: String = "",
    val maritalStatus: String = "",
    val nidOrPass: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val department: String = "",
    val position: String = "",
    val salary: String = ""
)
