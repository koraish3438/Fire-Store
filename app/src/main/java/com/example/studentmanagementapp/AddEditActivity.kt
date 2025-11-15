package com.example.studentmanagementapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementapp.databinding.ActivityAddEditBinding
import com.google.firebase.firestore.FirebaseFirestore

class AddEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditBinding
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var firebaseDocId: String? = null
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get intent data if editing
        firebaseDocId = intent.getStringExtra("firebaseDocId")
        isEditMode = firebaseDocId != null

        if (isEditMode) {
            binding.editTextEmployeeId.setText(intent.getStringExtra("employeeId"))
            binding.editTextName.setText(intent.getStringExtra("name"))
            binding.editTextDob.setText(intent.getStringExtra("dob"))
            binding.editTextGender.setText(intent.getStringExtra("gender"))
            binding.editTextMaritalStatus.setText(intent.getStringExtra("maritalStatus"))
            binding.editTextNidOrPass.setText(intent.getStringExtra("nidOrPass"))
            binding.editTextEmail.setText(intent.getStringExtra("email"))
            binding.editTextNumber.setText(intent.getStringExtra("phone"))
            binding.editTextAddress.setText(intent.getStringExtra("address"))
            binding.editTextDepartment.setText(intent.getStringExtra("department"))
            binding.editTextPosition.setText(intent.getStringExtra("position"))
            binding.editTextSalary.setText(intent.getStringExtra("salary"))
        }

        // Gender Dropdown
        val genderItems = listOf("Male", "Female", "Other")
        val genderAdapter = ArrayAdapter(this, R.layout.dropdown_item, genderItems)
        (binding.editTextGender as? AutoCompleteTextView)?.setAdapter(genderAdapter)

        // Marital Status Dropdown
        val maritalItems = listOf("Single", "Married", "Divorced", "Widowed")
        val maritalAdapter = ArrayAdapter(this, R.layout.dropdown_item, maritalItems)
        (binding.editTextMaritalStatus as? AutoCompleteTextView)?.setAdapter(maritalAdapter)

        // Save button
        binding.btnSaveProfile.setOnClickListener {
            saveProfile()
        }
    }

    private fun saveProfile() {
        val employee = Employee(
            employeeId = binding.editTextEmployeeId.text.toString(),
            name = binding.editTextName.text.toString(),
            dob = binding.editTextDob.text.toString(),
            gender = binding.editTextGender.text.toString(),
            maritalStatus = binding.editTextMaritalStatus.text.toString(),
            nidOrPass = binding.editTextNidOrPass.text.toString(),
            email = binding.editTextEmail.text.toString(),
            phone = binding.editTextNumber.text.toString(),
            address = binding.editTextAddress.text.toString(),
            department = binding.editTextDepartment.text.toString(),
            position = binding.editTextPosition.text.toString(),
            salary = binding.editTextSalary.text.toString()
        )

        if (isEditMode) {
            // Update existing
            db.collection("employee").document(firebaseDocId!!)
                .set(employee)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Update failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            // Add new
            db.collection("employee")
                .add(employee)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile added", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Add failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
