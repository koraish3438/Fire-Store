package com.example.studentmanagementapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementapp.databinding.ItemStudentBinding

class StudentAdapter(
    private val students: List<Student>,
    private val onItemClick: (Student) -> Unit,
    private val onDeleteClick: (Student) -> Unit
): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
       val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentItem = students[position]
        holder.binding.apply {
            tvName.text = currentItem.name
            tvDepartment.text = currentItem.department
            tvEmail.text = currentItem.email
            tvDob.text = currentItem.dob
            tvNumber.text = currentItem.phone

            root.setOnClickListener { onItemClick(currentItem) }
            root.setOnClickListener { 
                onDeleteClick(currentItem) 
                notifyItemRemoved(position)
            }
        }
    }

    override fun getItemCount(): Int = students.size


    class StudentViewHolder(val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root)
}