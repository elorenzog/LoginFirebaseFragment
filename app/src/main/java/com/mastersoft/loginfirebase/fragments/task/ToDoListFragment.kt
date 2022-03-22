package com.mastersoft.loginfirebase.fragments.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.mastersoft.loginfirebase.R
import com.mastersoft.loginfirebase.data.task.TaskViewModel
import com.mastersoft.loginfirebase.databinding.FragmentToDoListBinding

class ToDoListFragment : Fragment() {
    lateinit var binding: FragmentToDoListBinding
    lateinit var taskViewModel: TaskViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentToDoListBinding.inflate(layoutInflater)

        // Recycler view
        val adapter = ListAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        firebaseAuth = FirebaseAuth.getInstance()

        // TaskViewModel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.readAllTasks.observe(viewLifecycleOwner, Observer { task ->
            adapter.setData(task.filter { x -> x.user_id == firebaseAuth.currentUser!!.uid })
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_toDoListFragment_to_addTaskFragment)
        }

        binding.floatingActionButtonAccount.setOnClickListener {
            findNavController().navigate(R.id.action_toDoListFragment_to_profileFragment)
        }

        return binding.root
    }

}