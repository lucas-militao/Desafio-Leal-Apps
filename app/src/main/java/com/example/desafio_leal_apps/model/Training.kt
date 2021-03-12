package com.example.desafio_leal_apps.model

import java.sql.Timestamp

data class Training(
        val name: String,
        val description: String,
        val date: String,
        val exercise: ArrayList<Exercise>
)