package com.example.desafio_leal_apps.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.desafio_leal_apps.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding

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
                    Toast.makeText(context, "O usuário foi logado", Toast.LENGTH_LONG).show()
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
            Toast.makeText(context, "O usuário está logado!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Necessário fazer login!", Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToHome() {
    }
}