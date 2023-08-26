package com.example.borcdefteri.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PaymentWithPerson (
    @Embedded val payment:Payment,
    @Relation(
        parentColumn = "person_id",
        entityColumn = "person_id"
    )
    val person:Person
)