package com.mastersoft.loginfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.mastersoft.loginfirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure actionbar
        actionBar = supportActionBar!!
        actionBar.hide()


        val login = ""

        val bundle = Bundle()
//        bundle.put("value", this)
        bundle.putString("hello", login)

        setupActionBarWithNavController(findNavController(R.id.fragmentContainer))

//        val fragment = LoginFragment()
//        fragment.arguments = bundle
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.fragmentContainer, fragment)
//        transaction.addToBackStack(null)
//        transaction.commit()

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainer)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}