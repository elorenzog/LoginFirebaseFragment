package com.mastersoft.loginfirebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.view.children
import androidx.core.view.get
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mastersoft.loginfirebase.databinding.ActivitySignUpBinding
import java.time.LocalDate
import java.util.*
import kotlin.Exception
import kotlin.collections.HashMap

class SignUpActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivitySignUpBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var name = ""
    private var lastName = ""
    private var phoneNumber = ""
    private var email = ""
    private var password = ""
    private var sex = ""
    private var dateOfBirth = ""
    private var country = ""
    private var province = ""
    private var address = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        // Progress Dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        // handle click
        binding.signUpBtn.setOnClickListener{
            validateData()
        }

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        finish()
    }

    private fun validateData() {
        name = binding.nameEt.text.toString().trim()
        lastName = binding.lastNameEt.text.toString().trim()
        phoneNumber = binding.phoneNumberEt.text.toString().trim()
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()
        val radioButtonId = binding.sexEt.checkedRadioButtonId;
        val radioSexButton = findViewById<RadioButton>(radioButtonId)
        sex = radioSexButton.text.toString()
        dateOfBirth = binding.birthEt.text.toString()
        country = binding.countryEt.text.toString().trim()
        province = binding.provinceEt.text.toString().trim()
        address = binding.addressEt.text.toString().trim()

        // validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            binding.passwordEt.error = "Please enter password"
        }
        else if(password.length < 6){
            binding.passwordEt.error = "Password must at least characters long"
        }
        else{ // all data is validated
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        progressDialog.show()

        // create account
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Account created with email $email", Toast.LENGTH_SHORT).show()
                // data
                var user = User()
                user.userid = email.toString()
                user.name = name
                user.last_name = lastName
                user.phone_number = phoneNumber
                user.sex = sex
                user.date_of_birth = dateOfBirth
                user.country = country
                user.province = province
                user.address = address
//
                try {
                    database.child("users").child(firebaseUser.uid).setValue(user)
                    database.push()
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                }
                catch (e: Exception){
                    Toast.makeText(this,"An error has occurred", Toast.LENGTH_SHORT).show()
                }


            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this,"SignUp Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

class User {
    lateinit var name: String
    lateinit var last_name: String
    lateinit var phone_number: String
    lateinit var sex: String
    lateinit var date_of_birth: String
    lateinit var country: String
    lateinit var province: String
    lateinit var address: String
    lateinit var userid: String


}