package com.mastersoft.loginfirebase.fragments.task

import android.app.FragmentManager
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgument
import androidx.recyclerview.widget.RecyclerView
import com.mastersoft.loginfirebase.R
import com.mastersoft.loginfirebase.data.task.Task
import com.mastersoft.loginfirebase.databinding.CustomRowBinding
import com.mastersoft.loginfirebase.fragments.user.SignUpFragment

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private  var taskList = emptyList<Task>()
    lateinit var binding: CustomRowBinding

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = CustomRowBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding.root)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = taskList[position]
//        binding.taskId.text = currentItem.id.toString()
        binding.taskName.text = currentItem.name.toString()
        binding.taskDescription.text = currentItem.description.toString()

        binding.rowLayout.setOnClickListener {
            val action = ToDoListFragmentDirections.actionToDoListFragmentToUpdateTaskFragment(currentItem)
            holder.itemView.findNavController().navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return  taskList.size
    }

    fun setData(task: List<Task>){
        this.taskList = task
        notifyDataSetChanged()
    }
}