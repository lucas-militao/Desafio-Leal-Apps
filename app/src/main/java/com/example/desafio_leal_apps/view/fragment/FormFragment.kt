package com.example.desafio_leal_apps.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.desafio_leal_apps.R
import com.example.desafio_leal_apps.databinding.FragmentFormBinding
import com.example.desafio_leal_apps.model.Exercise
import com.example.desafio_leal_apps.model.Training
import com.example.desafio_leal_apps.view.activity.MainActivity
import com.example.desafio_leal_apps.view.adapter.ExerciseListAdapter
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.fragment_form.*
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

class FormFragment : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var exercises: ArrayList<Exercise>
    private lateinit var adapter: ExerciseListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentFormBinding.inflate(inflater)
        binding.lifecycleOwner = this

        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference

        exercises = ArrayList()

        setupView()

        exercises.add(Exercise(1212, "sdsds", "32323sdsdsd"))
        exercises.add(Exercise(15, "sdsds", "32323sdsdsd"))
        exercises.add(Exercise(1245412, "sdsds", "32323sdsdsd"))
        exercises.add(Exercise(67, "sdsds", "32323sdsdsd"))
        exercises.add(Exercise(1234312, "sdsds", "32323sdsdsd"))

        adapter.updateList(exercises)

        return binding.root
    }


    private fun setupView() {
        setHasOptionsMenu(true)

        (activity as MainActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "Novo Treino"
        }

        with(binding) {
            addExerciseFloatingButton.setOnClickListener {
                if (!isExerciseFieldsEmpty()) {
                    addExercise(
                            name = this.exerciseNameField.text.toString().toInt(),
                            image = this.exerciseImageField.text.toString(),
                            comments = this.exerciseCommentsField.text.toString()
                    )
                    cleanAllExerciseFields()
                } else {
                    Toast.makeText(context, "É necessário preencher os campos nome e observações", Toast.LENGTH_LONG).show()
                }
            }
        }

        adapter = ExerciseListAdapter()
        binding.exerciseList.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_form, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                (activity as MainActivity).onBackPressed()
                return false
            }

            R.id.saveTraining -> {
                if (!isTrainingFieldsEmpty()) {
                    saveTraining(
                            this.trainingNameTextField.text.toString().toInt(),
                            this.descriptionTextField.text.toString(),
                            exercises
                    )
                    (activity as MainActivity).onBackPressed()
                }
                else
                    Toast.makeText(context, "Necessário que todos os campos sejam preenchidos!", Toast.LENGTH_LONG).show()

                return false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveTraining(name: Int, description: String, exercises: ArrayList<Exercise>) {
        val newTraining = Training(
                name,
                description,
                ServerValue.TIMESTAMP,
                exercises
        )
        databaseReference.child("Treinos").child(newTraining.name.toString()).setValue(newTraining)
    }

    private fun isTrainingFieldsEmpty(): Boolean {
        return (trainingNameTextField.text.isNullOrEmpty() ||
                descriptionTextField.text.isNullOrEmpty())
    }

    private fun isExerciseFieldsEmpty(): Boolean {
        return (binding.exerciseNameField.text.isNullOrEmpty() ||
                binding.exerciseCommentsField.text.isNullOrEmpty())
    }

    private fun cleanAllExerciseFields() {
        this.exerciseNameField.setText("")
        this.exerciseImageField.setText("")
        this.exerciseCommentsField.setText("")
    }

    private fun addExercise(name: Int, image: String, comments: String) {
        exercises.add(Exercise(name, image, comments))
        adapter.updateList(exercises)
    }



//    private fun getExerciseData(snapshot: DataSnapshot) {
//        snapshot.getValue<Training>()?.exercise.let {
//            if (!it.isNullOrEmpty()) {
//                exercises = it
//                adapter.updateList(exercises)
//            }
//        }
//    }
}