package com.mastersoft.loginfirebase.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.mastersoft.loginfirebase.R
import com.mastersoft.loginfirebase.databinding.FragmentLoginBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // ViewBinding
    private lateinit var binding: FragmentLoginBinding

    // ProgressDialog
    private lateinit var actionBar: ActionBar

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    // FirebaseAuthorization
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
            val ls = requireArguments().getString("hello")
            binding.emailEt.setText(ls.toString())
        }



//         Configure progress dialog
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging...")
        progressDialog.setCanceledOnTouchOutside(false)


        // Init firebase authentication
        firebaseAuth = FirebaseAuth.getInstance()
        chekUser()

        // Sign up activity
        binding.noAccountTv.setOnClickListener{
            val login = "login"

            val bundle = Bundle()
            bundle.putString("hello", login)

            val fragment = SignUpFragment()
            fragment.arguments = bundle
            val transaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // Handle click, begin login
        binding.loginBtn.setOnClickListener{
            validateData()
        }

        binding.forgotAccountTv.setOnClickListener{
            val login = binding.emailEt.text.toString()

            val bundle = Bundle()
            bundle.putString("email", login)

            val fragment = ForgotPasswordFragment()
            fragment.arguments = bundle
            val transaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun validateData() {
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        // validate
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            binding.passwordEt.error = "Please enter password"
        }
        else{ // all data is validated
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                // get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                val transaction = requireFragmentManager().beginTransaction()
                val fragment = ProfileFragment()
                transaction.replace(R.id.fragmentContainer, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
                Toast.makeText(requireContext(),"LoggedIn as $email", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(requireContext(),"Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun chekUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            val transaction = requireFragmentManager().beginTransaction()
            val fragment = ProfileFragment()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}