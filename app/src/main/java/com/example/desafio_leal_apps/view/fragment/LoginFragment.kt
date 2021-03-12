package com.example.desafio_leal_apps.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.desafio_leal_apps.R
import com.example.desafio_leal_apps.databinding.FragmentLoginBinding
import com.example.desafio_leal_apps.view.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this

        setupView()

        return binding.root
    }

    private fun setupView() {
        navController = NavHostFragment.findNavController(this)

        (activity as MainActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.title = "Login"
        }

        binding.loginButton.setOnClickListener { login(
            email = emailTextField.text.toString(),
            password = passwordTextField.text.toString()
        ) }
    }

    @SuppressLint("ShowToast")
    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")
                    val user = auth.currentUser
                    navigateToHome()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun isUserConnected(): Boolean {
        return (auth.currentUser != null)
    }

    override fun onStart() {
        super.onStart()
        if (isUserConnected()) {
            navigateToHome()
        } else {
            return
        }
    }

    private fun navigateToHome() {
        navController.navigate(R.id.action_loginFragment_to_homeFragment)
    }
}