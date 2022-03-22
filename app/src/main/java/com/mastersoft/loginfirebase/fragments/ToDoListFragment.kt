package com.mastersoft.loginfirebase.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mastersoft.loginfirebase.R
import com.mastersoft.loginfirebase.databinding.ActivityMainBinding
import com.mastersoft.loginfirebase.databinding.FragmentToDoListBinding

class ToDoListFragment : Fragment() {
    lateinit var binding: FragmentToDoListBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentToDoListBinding.inflate(layoutInflater)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_toDoListFragment_to_addTaskFragment)
        }

        return binding.root
    }

}