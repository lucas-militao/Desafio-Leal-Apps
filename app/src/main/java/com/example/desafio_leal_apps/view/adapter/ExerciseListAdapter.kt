package com.example.desafio_leal_apps.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafio_leal_apps.R
import com.example.desafio_leal_apps.databinding.ItemExerciseBinding
import com.example.desafio_leal_apps.model.Exercise

class ExerciseListAdapter : RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>() {

    private var exercises = ArrayList<Exercise>()

    inner class ViewHolder(private var binding: ItemExerciseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.exerciseName.text = exercise.name.toString()
            binding.exerciseComment.text = exercise.comments
            Glide
                .with(itemView)
                .load(exercise.image)
                .centerCrop()
                .error(R.drawable.image_exercicio_generico)
                .into(binding.exerciseImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemExerciseBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = exercises.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    fun updateList(list: ArrayList<Exercise>) {
        exercises = list
        notifyDataSetChanged()
    }

}