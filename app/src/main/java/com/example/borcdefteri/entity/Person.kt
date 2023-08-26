package com.example.borcdefteri.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "persons")
data class Person(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "person_id")@NotNull var person_id:Int,
    @ColumnInfo(name = "person_name")@NotNull var person_name:String,
    @ColumnInfo(name = "person_tel")@NotNull var person_tel:String,
    @ColumnInfo(name = "address")@NotNull var person_address:String,
    @ColumnInfo(name = "notes")@NotNull var  person_notes:String
)
