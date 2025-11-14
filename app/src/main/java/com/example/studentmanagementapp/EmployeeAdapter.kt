package com.example.studentmanagementapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementapp.databinding.ItemEmployeeBinding

class EmployeeAdapter(
    private val employees: List<Employee>,
    private val onItemClick: (Employee) -> Unit,
    private val onDeleteClick: (Employee) -> Unit
): RecyclerView.Adapter<EmployeeAdapter.StudentViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
       val binding = ItemEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentItem = employees[position]
        holder.binding.apply { 
            tvName.text = currentItem.name
            tvDepartment.text = currentItem.department
            tvEmail.text = currentItem.email
            tvDob.text = currentItem.dob
            tvNumber.text = currentItem.phone


        }
    }

    override fun getItemCount(): Int = employees.size


    class StudentViewHolder(val binding: ItemEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root)
}