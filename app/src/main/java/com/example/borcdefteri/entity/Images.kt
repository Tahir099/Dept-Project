package com.example.borcdefteri.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "person_images",
    foreignKeys = [
        ForeignKey(
            entity = Person::class,
            parentColumns = ["person_id"],
            childColumns = ["person_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]

)
data class Images(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "image_id")@NotNull var image_id:Int,
    @ColumnInfo(name = "person_id")@NotNull var person_id:Int,
    @ColumnInfo(name = "image")@NotNull var image:String
)