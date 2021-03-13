package com.example.desafio_leal_apps.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.desafio_leal_apps.databinding.FragmentFormBinding
import com.example.desafio_leal_apps.model.Exercise
import com.example.desafio_leal_apps.model.Training
import com.example.desafio_leal_apps.view.activity.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_form.*
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.ArrayList

class FormFragment : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentFormBinding.inflate(inflater)
        binding.lifecycleOwner = this

        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference

        setupView()

        return binding.root
    }


    private fun setupView() {
        setHasOptionsMenu(true)

        (activity as MainActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "Formulário"
        }

        with(binding) {
            this.saveTrainingButton.setOnClickListener {
                if (!isEmpty()) {
                    saveTraining(
                            this.trainingNameTextField.text.toString().toInt(),
                            this.descriptionTextField.text.toString()
                    )
                    (activity as MainActivity).onBackPressed()
                }
                else
                    Toast.makeText(context, "Necessário que todos os campos sejam preenchidos!", Toast.LENGTH_LONG).show()
            }
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

    private fun saveTraining(name: Int, description: String) {
        val newTraining = Training(
                name,
                description,
                Timestamp(Date().time),
                ArrayList()
        )
        databaseReference.child("Treinos").child(newTraining.name.toString()).setValue(newTraining)
    }

    private fun isEmpty(): Boolean {
        return (trainingNameTextField.text.isNullOrEmpty() || descriptionTextField.text.isNullOrEmpty())
    }
}