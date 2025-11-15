package com.example.studentmanagementapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentmanagementapp.databinding.ActivityEmployeeDetailsBinding
import com.example.studentmanagementapp.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: EmployeeAdapter
    private val employeeList = mutableListOf<Employee>()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var snapshotListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchEmployees()

        binding.btnAddProfile.setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        adapter = EmployeeAdapter(
            employees = employeeList,
            onItemClick = { employee ->
                val intent = Intent(this, EmployeeDetailsActivity::class.java)
                intent.putExtra("firebaseDocId", employee.firebaseDocId)
                intent.putExtra("employeeId", employee.employeeId)
                intent.putExtra("name", employee.name)
                intent.putExtra("dob", employee.dob)
                intent.putExtra("gender", employee.gender)
                intent.putExtra("maritalStatus", employee.maritalStatus)
                intent.putExtra("nidOrPass", employee.nidOrPass)
                intent.putExtra("email", employee.email)
                intent.putExtra("phone", employee.phone)
                intent.putExtra("address", employee.address)
                intent.putExtra("department", employee.department)
                intent.putExtra("position", employee.position)
                intent.putExtra("salary", employee.salary)
                startActivity(intent)
            },
            onEditClick = { employee ->
                val intent = Intent(this, AddEditActivity::class.java)
                intent.putExtra("firebaseDocId", employee.firebaseDocId)
                intent.putExtra("employeeId", employee.employeeId)
                intent.putExtra("name", employee.name)
                intent.putExtra("dob", employee.dob)
                intent.putExtra("gender", employee.gender)
                intent.putExtra("maritalStatus", employee.maritalStatus)
                intent.putExtra("nidOrPass", employee.nidOrPass)
                intent.putExtra("email", employee.email)
                intent.putExtra("phone", employee.phone)
                intent.putExtra("address", employee.address)
                intent.putExtra("department", employee.department)
                intent.putExtra("position", employee.position)
                intent.putExtra("salary", employee.salary)
                startActivity(intent)
            },
            onDeleteClick = { employee ->
                employee.firebaseDocId?.let { id ->
                    db.collection("employee").document(id)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Employee deleted", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Delete failed: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        )

        binding.recyclerViewProfiles.adapter = adapter
        binding.recyclerViewProfiles.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchEmployees() {
        snapshotListener = db.collection("employee")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }
                employeeList.clear()
                snapshot?.documents?.forEach { doc ->
                    val emp = doc.toObject(Employee::class.java)
                    emp?.firebaseDocId = doc.id
                    emp?.let { employeeList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }
    }

    override fun onDestroy() {
        snapshotListener?.remove()
        super.onDestroy()
    }
}
