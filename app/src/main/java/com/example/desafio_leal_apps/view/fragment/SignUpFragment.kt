package com.example.desafio_leal_apps.view.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.desafio_leal_apps.databinding.FragmentSignUpBinding
import com.example.desafio_leal_apps.view.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)
        binding.lifecycleOwner = this

        auth = FirebaseAuth.getInstance()
        setupView()

        return binding.root
    }

    private fun setupView() {
        binding.singUpButton.setOnClickListener {
            if (!isSignUpFieldsEmpty())
                signUp(binding.emailTextField.text.toString(), binding.passwordTextField.text.toString())
            else
                Toast.makeText(context, "É necessário que todos os campos estejam preenchidos", Toast.LENGTH_LONG).show()
        }

        binding.cancelButton.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        (activity as MainActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "Cadastro"
        }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")

                    (activity as MainActivity).onBackPressed()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Verifique se os campos estão corretos",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun isSignUpFieldsEmpty(): Boolean {
        with(binding) {
            return (this.emailTextField.text.isNullOrEmpty() || this.passwordTextField.text.isNullOrEmpty())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                (activity as MainActivity).onBackPressed()
                return false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}