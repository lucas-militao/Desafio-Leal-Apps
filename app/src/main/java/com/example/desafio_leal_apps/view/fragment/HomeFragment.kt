package com.example.desafio_leal_apps.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.desafio_leal_apps.R
import com.example.desafio_leal_apps.databinding.FragmentHomeBinding
import com.example.desafio_leal_apps.view.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        auth = FirebaseAuth.getInstance()

        setupView()

        return binding.root
    }

    private fun setupView() {
        setHasOptionsMenu(true)
        navController = NavHostFragment.findNavController(this)

        with(binding) {
            this.addFloatingButton.setOnClickListener { navigateToForm() }
        }

        (activity as MainActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "Principal"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                auth.signOut()
                (activity as MainActivity).onBackPressed()
                return false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToForm() {
        navController.navigate(R.id.action_homeFragment_to_formFragment)
    }
}