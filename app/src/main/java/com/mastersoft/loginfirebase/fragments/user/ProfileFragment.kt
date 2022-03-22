package com.mastersoft.loginfirebase.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mastersoft.loginfirebase.R
import com.mastersoft.loginfirebase.data.task.TaskViewModel
import com.mastersoft.loginfirebase.data.user.User
import com.mastersoft.loginfirebase.data.user.UserViewModel
import com.mastersoft.loginfirebase.databinding.FragmentProfileBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var userFound: List<User>

    // ViewBinding
    private lateinit var binding: FragmentProfileBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // FirebaseAuthorization
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Init firebase authentication
        firebaseAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        chekUser()

        binding.logOutBtn.setOnClickListener{
            firebaseAuth.signOut()
            chekUser()
        }

        binding.floatingActionButtonEdit.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_updateProfileFragment)
        }


    }

    private fun chekUser() {
        // check user is logged
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val adapter = ListController()
            try {
                // texts


                userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
                val userFound = userViewModel.readOneUser(firebaseUser.uid)

//                assert(userFound, )
//
//                userFound = adapter.taskList[0]
//                (getString(R.string.name_profile) + userFound.name).also { binding.nameProfileEt.text = it }
//                binding.lastNameProfileEt.text = getString(R.string.lastname_profile) + userFound.last_name
//                binding.phoneNumberProfileEt.text = getString(R.string.phone_number_profile) + userFound.phone_number
//                binding.sexProfileEt.text = getString(R.string.sex_profile) + userFound.sex
//                binding.emailProfileEt.text =  getString(R.string.email_profile) + userFound.userid
//                binding.countryProfileEt.text = getString(R.string.country_profile) + userFound.country
//                binding.provinceProfileEt.text = getString(R.string.province_profile) + userFound.province
//                binding.addressProfileEt.text = getString(R.string.address_profile) + userFound.address
//                binding.birthProfileEt.text = getString(R.string.birth_date_profile) + userFound.date_of_birth
            }
            catch (e: Exception){
                Toast.makeText(requireContext(),"Error getting data ${e.message}", Toast.LENGTH_SHORT).show()
            }




        }
        else {
            val transaction = requireFragmentManager().beginTransaction()
            val fragment = LoginFragment()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}