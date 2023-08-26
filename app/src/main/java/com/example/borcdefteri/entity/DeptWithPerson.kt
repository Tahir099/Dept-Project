package com.example.borcdefteri.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DeptWithPerson(
    @Embedded val dept:Dept,
    @Relation(
        parentColumn = "person_id",
        entityColumn = "person_id"
    )
    val person: Person
)