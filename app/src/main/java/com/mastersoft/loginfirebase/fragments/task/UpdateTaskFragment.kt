package com.mastersoft.loginfirebase.fragments.task

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mastersoft.loginfirebase.R
import com.mastersoft.loginfirebase.data.task.Task
import com.mastersoft.loginfirebase.data.task.TaskViewModel
import kotlinx.android.synthetic.main.fragment_update_task.*
import kotlinx.android.synthetic.main.fragment_update_task.view.*

class UpdateTaskFragment : Fragment() {

    private val args by navArgs<UpdateTaskFragmentArgs>()
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_task, container, false)
        view.nameUTaskEt.setText(args.currentTask.name)
        view.descriptionUTaskEt.setText(args.currentTask.description)

        view.updateTaskBtn.setOnClickListener {
            updateItem()
        }

        view.floatingActionButtonDelete.setOnClickListener {
            deleteItem()
        }

        return view
    }

    private fun deleteItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            taskViewModel.deleteTask(args.currentTask)
            Toast.makeText(requireContext(), "Successfully removed: ${args.currentTask.name}!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateTaskFragment_to_toDoListFragment)

        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete ${args.currentTask.name}?")
        builder.setMessage("Are you sure you want to delete ${args.currentTask.name}?")
        builder.show()
    }

    private fun updateItem() {
        val name = nameUTaskEt.text.toString()
        val description = descriptionUTaskEt.text.toString()

        if (inputCheck(name, description)){
            val updatedTask = Task(args.currentTask.id,args.currentTask.user_id,name, description,false)
            taskViewModel.updateTask(updatedTask)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateTaskFragment_to_toDoListFragment)
        }else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(name: String, description: String): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(description))
    }


}