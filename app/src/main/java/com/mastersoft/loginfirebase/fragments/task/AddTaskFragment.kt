package com.mastersoft.loginfirebase.fragments.task

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.mastersoft.loginfirebase.R
import com.mastersoft.loginfirebase.data.task.Task
import com.mastersoft.loginfirebase.data.task.TaskViewModel
import com.mastersoft.loginfirebase.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(layoutInflater)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.saveTaskBtn.setOnClickListener {
            insertDataToDatabase()
        }



        // Inflate the layout for this fragment
        return binding.root
    }

    private fun insertDataToDatabase() {

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val user_id = firebaseUser.uid
            val name = binding.nameTaskEt.text.toString()
            val description = binding.descriptionTaskEt.text.toString()
            val completed = false

            if(inputCheck(name, description)) {
                val task = Task(0, user_id, name, description, completed)

                taskViewModel.addTask(task)
                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_addTaskFragment_to_toDoListFragment)
            }else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun inputCheck(name: String, description: String): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(description))
    }

}