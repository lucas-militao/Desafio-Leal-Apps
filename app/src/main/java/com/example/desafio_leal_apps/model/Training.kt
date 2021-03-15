package com.example.desafio_leal_apps.model

import com.google.firebase.database.Exclude

data class Training(
        val user: String = "",
        val name: Int? = 0,
        val description: String? = "",
        val createdDate: Long,
        val exercise: ArrayList<Exercise>? = null
) {

    constructor() : this("",0, "", 0, null)

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "name" to name,
                "description" to description,
                "exercise" to exercise,
                "createdDate" to createdDate
        )
    }
}