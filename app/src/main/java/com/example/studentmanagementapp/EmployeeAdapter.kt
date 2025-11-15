package com.example.studentmanagementapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementapp.databinding.ItemEmployeeBinding

class EmployeeAdapter(
    private val employees: List<Employee>,
    private val onItemClick: (Employee) -> Unit,
    private val onEditClick: (Employee) -> Unit,
    private val onDeleteClick: (Employee) -> Unit
): RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = ItemEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val currentEmployee = employees[position]
        holder.binding.apply {
            tvEmployeeId.text = "ID: ${currentEmployee.employeeId}"
            tvName.text = currentEmployee.name
            tvEmail.text = currentEmployee.email

            btnEdit.setOnClickListener { onEditClick(currentEmployee) }
            btnDelete.setOnClickListener { onDeleteClick(currentEmployee) }

            root.setOnClickListener {
                onItemClick(currentEmployee)
            }
        }
    }

    override fun getItemCount(): Int = employees.size

    class EmployeeViewHolder(val binding: ItemEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root)
}