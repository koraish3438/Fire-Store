package com.example.studentmanagementapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

        setRecyclerView()
        fetchDataFromFirestore()

        binding.btnAddProfile.setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }
    }

    private fun setRecyclerView() {
        adapter = EmployeeAdapter(
            employees = employeeList,
            onEditClick = { employee ->
                val intent = Intent(this, AddEditActivity::class.java)
                intent.putExtra("employee", employee)
                startActivity(intent)
            },
            onDeleteClick = { employee ->
                deleteEmployee(employee)
            },
            onItemClick = { employee ->
                val intent = Intent(this, EmployeeDetailsActivity::class.java)
                intent.putExtra("employee", employee)
                startActivity(intent)
            }
        )

        binding.recyclerViewProfiles.adapter = adapter
        binding.recyclerViewProfiles.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchDataFromFirestore() {
        snapshotListener = db.collection("employee")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(this, "Error fetching data: ${error.message}", Toast.LENGTH_SHORT).show()
                }

                employeeList.clear()
                snapshot?.documents?.forEach { document ->
                    val employees = document.toObject(Employee::class.java)
                    if(employees != null) {
                        employees.firebaseDocId = document.id
                        employeeList.add(employees)
                    }
                }
                adapter.notifyDataSetChanged()
            }

    }

    private fun deleteEmployee(employee: Employee) {
        val docId = employee.firebaseDocId?: return

        db.collection("employee").document(docId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Employee deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error deleting employee: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroy() {
        snapshotListener?.remove()
        super.onDestroy()
    }
}