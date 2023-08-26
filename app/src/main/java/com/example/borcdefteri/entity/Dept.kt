package com.example.borcdefteri.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "dept",
    foreignKeys = [
        ForeignKey(
            entity = Person::class,
            parentColumns = ["person_id"],
            childColumns = ["person_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Dept(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dept_id")@NotNull var dept_id:Int,
    @ColumnInfo(name = "person_id")@NotNull var person_id:Int,
    @ColumnInfo(name = "date")@NotNull var date:String,
    @ColumnInfo(name = "procedure")@NotNull var procedure:String,
    @ColumnInfo(name = "note")@NotNull var note:String,
    @ColumnInfo(name = "cost")@NotNull var cost:Int
)
