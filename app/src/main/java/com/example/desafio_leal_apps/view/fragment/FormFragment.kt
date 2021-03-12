package com.example.desafio_leal_apps.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.desafio_leal_apps.databinding.FragmentFormBinding
import com.example.desafio_leal_apps.model.Exercise
import com.example.desafio_leal_apps.model.Training
import com.example.desafio_leal_apps.view.activity.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_form.*
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupView() {
        setHasOptionsMenu(true)

        (activity as MainActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "Formulário"
        }

        binding.saveButton.setOnClickListener { saveNewTraining() }
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveNewTraining() {
        val exerciseList = ArrayList<Exercise>()
        exerciseList.add(Exercise(
            1,
            "blablabla",
            "bate bunda"
        ))
        val newTraining = Training(
            UUID.randomUUID().toString(),
            "Um treino bacana para morrer",
            DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
            exerciseList
        )
        databaseReference.child("Treinos").child(newTraining.name).setValue(newTraining)
    }
}