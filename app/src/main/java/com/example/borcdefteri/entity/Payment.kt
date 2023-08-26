package com.example.borcdefteri.entity

import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(
    tableName = "payments",
    foreignKeys = [
        ForeignKey(
            entity = Person::class,
            parentColumns = ["person_id"],
            childColumns = ["person_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Payment(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "payment_id")@NotNull var payment_id:Int,
    @ColumnInfo(name = "person_id")@NotNull var person_id:Int,
    @ColumnInfo(name = "amount")@NotNull var amount:Int,
    @ColumnInfo(name = "date")@NotNull var date:String
)
