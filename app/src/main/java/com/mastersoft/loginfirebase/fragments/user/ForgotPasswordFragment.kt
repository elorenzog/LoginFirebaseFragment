package com.mastersoft.loginfirebase.fragments.user

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.mastersoft.loginfirebase.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {

    // ViewBinding
    private lateinit var binding: FragmentForgotPasswordBinding

    // FirebaseAuthorization
    private lateinit var firebaseAuth: FirebaseAuth

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    private  lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            val ls = requireArguments().getString("email")
            binding.emailRecoverEt.setText(ls.toString())
        }

        // Init firebase authentication
        firebaseAuth = FirebaseAuth.getInstance()

        // Configure actionbar
//        actionBar = requireActivity().actionBar.
//        actionBar.title = "Password Recovery"

        // Configure progress dialog
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Sending mail...")
        progressDialog.setCanceledOnTouchOutside(false)

        val email = ""
//        email = requireContext().findViewById<EditText>(R.id.emailEt).text.toString       ()
        var emailAddress = ""

        emailAddress = email.ifEmpty {
            binding.emailRecoverEt.text.toString()
        }

        binding.loginBtn.setOnClickListener {

            progressDialog.show()
            emailAddress = binding.emailRecoverEt.text.toString()

            try{
                firebaseAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(),"The recovery email has been sent ", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
//                            startActivity(Intent(this, LoginActivity::class.java))
                        }
                    }
                    .addOnFailureListener{
                        progressDialog.dismiss()
                        binding.emailRecoverEt.error = "An error has occurred, please check your email"
                        Toast.makeText(requireContext(),"The recovery email has not been sent ", Toast.LENGTH_SHORT).show()

                    }
            }catch (e: Exception){
                Toast.makeText(requireContext(),"Please write your email address", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }

        }
    }

}