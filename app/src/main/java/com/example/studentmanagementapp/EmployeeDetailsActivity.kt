package com.example.studentmanagementapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementapp.databinding.ActivityEmployeeDetailsBinding

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val employeeId = intent.getStringExtra("employeeId") ?: ""
        val name = intent.getStringExtra("name") ?: ""
        val dob = intent.getStringExtra("dob") ?: ""
        val gender = intent.getStringExtra("gender") ?: ""
        val maritalStatus = intent.getStringExtra("maritalStatus") ?: ""
        val nidOrPass = intent.getStringExtra("nidOrPass") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        val phone = intent.getStringExtra("phone") ?: ""
        val address = intent.getStringExtra("address") ?: ""
        val department = intent.getStringExtra("department") ?: ""
        val position = intent.getStringExtra("position") ?: ""
        val salary = intent.getStringExtra("salary") ?: ""


        binding.tvDetailEmployeeId.text = employeeId
        binding.tvDetailName.text = name
        binding.tvDetailDob.text = dob
        binding.tvDetailGender.text = gender
        binding.tvDetailMaritalStatus.text = maritalStatus
        binding.tvDetailNidOrPass.text = nidOrPass
        binding.tvDetailEmail.text = email
        binding.tvDetailPhone.text = phone
        binding.tvDetailAddress.text = address
        binding.tvDetailDepartment.text = department
        binding.tvDetailPosition.text = position
        binding.tvDetailSalary.text = salary

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}