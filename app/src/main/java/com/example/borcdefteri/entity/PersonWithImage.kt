package com.example.borcdefteri.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PersonWithImage(
    @Embedded val person:Person,
    @Relation(
        parentColumn = "person_id",
        entityColumn = "person_id"
    )
    val image:Images?
)