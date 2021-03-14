package com.example.desafio_leal_apps.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_leal_apps.databinding.ItemExerciseBinding
import com.example.desafio_leal_apps.databinding.ItemTrainingBinding
import com.example.desafio_leal_apps.model.Training

class TrainingListAdapter(var onDelete: () -> Unit, var onEdit: () -> Unit): RecyclerView.Adapter<TrainingListAdapter.ViewHolder>() {

    private var trainings = ArrayList<Training>()

    inner class ViewHolder (private var binding: ItemTrainingBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(training: Training, onDelete: () -> Unit, onEdit: () -> Unit) {
            binding.trainingName.text = training.name.toString()
            binding.trainingDescription.text = training.description
            binding.deleteTraining.setOnClickListener { onDelete() }
            binding.editTraining.setOnClickListener { onEdit() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTrainingBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = trainings.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val training = trainings[position]
        holder.bind(
                training = training,
                onEdit = onEdit,
                onDelete = onDelete
        )
    }

    fun updateList(list: ArrayList<Training>) {
        trainings = list
        notifyDataSetChanged()
    }
}