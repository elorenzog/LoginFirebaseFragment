package com.mastersoft.loginfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.mastersoft.loginfirebase.databinding.ActivityProfileBinding
import java.util.*

class ProfileActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding:ActivityProfileBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // FirebaseAuthorization
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Configure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        // Init firebase authentication
        firebaseAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        chekUser()

        binding.logOutBtn.setOnClickListener{
            firebaseAuth.signOut()
            chekUser()
        }
    }


    private fun chekUser() {
        // check user is logged
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {

            try {
                // texts
                val data = database.child("users").child(firebaseUser.uid).get().addOnSuccessListener {

                    var userFound = it.getValue<User>()
                    if (userFound != null) {
                        binding.nameProfileEt.text = getString(R.string.name_profile) + userFound.name
                        binding.lastNameProfileEt.text = getString(R.string.lastname_profile) + userFound.last_name
                        binding.phoneNumberProfileEt.text = getString(R.string.phone_number_profile) + userFound.phone_number
                        binding.sexProfileEt.text = getString(R.string.sex_profile) + userFound.sex
                        binding.emailProfileEt.text =  getString(R.string.email_profile) + userFound.userid
                        binding.countryProfileEt.text = getString(R.string.country_profile) + userFound.country
                        binding.provinceProfileEt.text = getString(R.string.province_profile) + userFound.province
                        binding.addressProfileEt.text = getString(R.string.address_profile) + userFound.address
                        binding.birthProfileEt.text = getString(R.string.birth_date_profile) + userFound.date_of_birth
                    }
                }.addOnFailureListener{
                    Toast.makeText(this,"Error getting data from firebase", Toast.LENGTH_SHORT).show()
                    Log.e("firebase", "Error getting data", it)
                }
            }
            catch (e: Exception){
                Toast.makeText(this,"Error getting data ${e.message}", Toast.LENGTH_SHORT).show()
            }




        }
        else {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}
