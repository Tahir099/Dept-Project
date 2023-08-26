package com.example.borcdefteri.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.borcdefteri.entity.Person
import com.example.borcdefteri.entity.PersonWithImage


@Dao
interface PersonDao {
    @Insert
    suspend fun addPerson(person: Person): Long

    @Query("SELECT p.person_id, p.person_name, p.person_tel, p.address, p.notes, pi.image_id, pi.image\n" +
            "FROM persons p\n" +
            "LEFT JOIN person_images pi ON p.person_id = pi.person_id ORDER BY p.person_id DESC")
    suspend fun getPersons(): List<PersonWithImage>

    @Query("SELECT p.person_id, p.person_name, p.person_tel, p.address, p.notes, pi.image_id, pi.image\n" +
            "FROM persons p\n" +
            "LEFT JOIN person_images pi ON p.person_id = pi.person_id WHERE person_name LIKE '%'|| :word ||'%' ORDER BY p.person_id DESC ")
    suspend fun searchPerson(word:String): List<PersonWithImage>

    @Delete
    suspend fun deletePerson(person: Person)

    @Update
    suspend fun updatePerson(person: Person)
}


