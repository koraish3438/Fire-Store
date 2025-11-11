package com.example.studentmanagementapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementapp.databinding.ActivityAddEditBinding
import com.google.firebase.firestore.FirebaseFirestore

class AddEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditBinding
    private val db = FirebaseFirestore.getInstance()
    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentId = intent.getStringExtra("studentId")

        if (studentId != null) {
            loadStudentData()
        }

        binding.btnSaveProfile.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val department = binding.editTextDepartment.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val dob = binding.editTextDob.text.toString().trim()
            val phone = binding.editTextNumber.text.toString().trim()

            // Validation check (optional)
            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val studentData = hashMapOf(
                "name" to name,
                "department" to department,
                "email" to email,
                "dob" to dob,
                "phone" to phone
            )

            if (studentId != null) {
                // Update existing student
                db.collection("students").document(studentId!!)
                    .set(studentData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Student data updated successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to update student data", Toast.LENGTH_SHORT).show()
                    }
            } else {
                // Add new student
                db.collection("students")
                    .add(studentData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Student data added successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to add student data", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun loadStudentData() {
        db.collection("students").document(studentId!!)
            .get()
            .addOnSuccessListener { doc ->
                doc?.let {
                    binding.editTextName.setText(it.getString("name"))
                    binding.editTextDepartment.setText(it.getString("department"))
                    binding.editTextEmail.setText(it.getString("email"))
                    binding.editTextDob.setText(it.getString("dob"))
                    binding.editTextNumber.setText(it.getString("phone"))
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
    }
}
