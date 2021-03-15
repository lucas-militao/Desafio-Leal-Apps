package com.example.desafio_leal_apps.model

import com.google.firebase.database.Exclude

data class Exercise (
        var name: Int? = 0,
        var image: String? = "",
        var comments: String? = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "name" to name,
                "image" to image,
                "comments" to comments
        )
    }
}