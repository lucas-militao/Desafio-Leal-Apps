package com.example.desafio_leal_apps.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafio_leal_apps.R
import com.example.desafio_leal_apps.databinding.FragmentEditTrainingBinding
import com.example.desafio_leal_apps.databinding.FragmentLoginBinding

class EditTrainingFragment : Fragment() {

    private lateinit var binding: FragmentEditTrainingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTrainingBinding.inflate(inflater)
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun setupView() {

    }

}