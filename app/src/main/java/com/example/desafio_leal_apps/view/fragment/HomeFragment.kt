package com.example.desafio_leal_apps.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.desafio_leal_apps.R
import com.example.desafio_leal_apps.databinding.FragmentHomeBinding
import com.example.desafio_leal_apps.model.Training
import com.example.desafio_leal_apps.view.activity.MainActivity
import com.example.desafio_leal_apps.view.adapter.TrainingListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var trainings: ArrayList<Training>
    private lateinit var adapter: TrainingListAdapter

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
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference

        setupView()
        subscribeUI()

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

        adapter = TrainingListAdapter(
                onDelete = {
                    if (it.name != null)
                        deleteTraining(it.name)
                },
                onEdit = {}
        )

        trainings = ArrayList()
        binding.trainingList.adapter = adapter
        adapter.updateList(trainings)
    }

    private fun subscribeUI() {
        eventDatabase()
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

    private fun eventDatabase() {
        val childEventListener = object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                updateList(snapshot)
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                updateList(snapshot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                updateList(snapshot)
            }
        }

        databaseReference.addChildEventListener(childEventListener)
    }

    private fun updateList(data: DataSnapshot) {
        trainings.clear()
        for (value in data.children) {
            val training = value.getValue<Training>()
            if (training != null) {
                if (training.user == auth.currentUser.uid)
                    trainings.add(training)
            }
        }
        adapter.updateList(trainings)
    }

    private fun deleteTraining(nome: Int) {
        databaseReference.child("Treinos").child(nome.toString()).removeValue()
    }
}