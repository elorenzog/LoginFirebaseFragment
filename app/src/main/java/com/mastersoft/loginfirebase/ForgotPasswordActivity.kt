package com.mastersoft.loginfirebase

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mastersoft.loginfirebase.databinding.ActivityForgotPasswordBinding
import com.mastersoft.loginfirebase.databinding.ActivityLoginBinding

class ForgotPasswordActivity : AppCompatActivity() {
    // ViewBinding
    private lateinit var binding: ActivityForgotPasswordBinding

    // FirebaseAuthorization
    private lateinit var firebaseAuth: FirebaseAuth

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init firebase authentication
        firebaseAuth = FirebaseAuth.getInstance()

        // Configure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Password Recovery"

        // Configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Sending mail...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.loginBtn.setOnClickListener {
            val emailAddress = binding.emailRecoverEt.text.toString()

            progressDialog.show()

            try{
                firebaseAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this,"The recovery email has been sent ", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                            startActivity(Intent(this, LoginActivity::class.java))
                        }
                    }
                    .addOnFailureListener{
                        progressDialog.dismiss()
                        binding.emailRecoverEt.error = "An error has occurred, please check your email"
                        Toast.makeText(this,"The recovery email has not been sent ", Toast.LENGTH_SHORT).show()

                    }
            }catch (e: Exception){
                Toast.makeText(this,"Please write your email address", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }

        }
    }


}