package com.example.desafio_leal_apps.model

import java.sql.Timestamp

data class Training(
        val name: Int? = 0,
        val description: String? = "",
        val date: Timestamp? = null,
        val exercise: ArrayList<Exercise>? = null
)