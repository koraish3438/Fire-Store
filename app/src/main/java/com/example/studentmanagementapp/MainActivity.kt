package com.example.studentmanagementapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentmanagementapp.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()
    private val studentsList = mutableListOf<Employee>()
    private lateinit var adapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchStudents()

       adapter = EmployeeAdapter(studentsList,
            onItemClick = { student ->
                val intent = Intent(this, AddEditActivity::class.java)
                intent.putExtra("studentId", student.id)
                startActivity(intent)
            },
            onDeleteClick = { student ->
                db.collection("students").document(student.id).delete()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error deleting student: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        )

        binding.recyclerViewProfiles.adapter = adapter
        binding.recyclerViewProfiles.layoutManager = LinearLayoutManager(this)

        binding.btnAddProfile.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
        }


    }

    private fun fetchStudents() {
        db.collection("students")
            .addSnapshotListener { snapshot, e->
                if (e != null) {
                    Toast.makeText(this, "Error fetching students: ${e.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }
                studentsList.clear()
                snapshot?.forEach { doc -> 
                    val employee = doc.toObject(Employee::class.java)
                    studentsList.add(employee)
                }
                adapter.notifyDataSetChanged()
            }
    }
}