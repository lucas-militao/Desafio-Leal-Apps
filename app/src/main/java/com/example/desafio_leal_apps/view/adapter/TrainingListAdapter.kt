package com.example.desafio_leal_apps.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_leal_apps.convertLongToTime
import com.example.desafio_leal_apps.databinding.ItemExerciseBinding
import com.example.desafio_leal_apps.databinding.ItemTrainingBinding
import com.example.desafio_leal_apps.model.Training

class TrainingListAdapter(var onDelete: (training: Training) -> Unit, var onEdit: (training: Training) -> Unit): RecyclerView.Adapter<TrainingListAdapter.ViewHolder>() {

    private var trainings = ArrayList<Training>()

    inner class ViewHolder (private var binding: ItemTrainingBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(training: Training, onDelete: (training: Training) -> Unit, onEdit: (training: Training) -> Unit) {
            binding.trainingName.text = training.name.toString()
            binding.trainingDescription.text = training.description
            binding.trainingData.text = convertLongToTime(training.createdDate)
            binding.deleteTraining.setOnClickListener { onDelete(training) }
            binding.editTraining.setOnClickListener { onEdit(training) }
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