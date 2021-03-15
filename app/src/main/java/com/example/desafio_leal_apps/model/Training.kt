package com.example.desafio_leal_apps.model

import com.google.firebase.database.Exclude
import java.sql.Timestamp

data class Training(
        val name: Int? = 0,
        val description: String? = "",
        val date: MutableMap<String, String>? = HashMap(),
        val exercise: ArrayList<Exercise>? = null
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "name" to name,
                "description" to description,
                "exercise" to exercise
        )
    }
}