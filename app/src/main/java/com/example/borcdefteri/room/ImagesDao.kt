package com.example.borcdefteri.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.borcdefteri.entity.Images

@Dao
interface ImagesDao {
    @Insert
    suspend fun addImage(image:Images)

    @Query("SELECT * FROM person_images WHERE person_id = :person_id")
    suspend fun getImageById(person_id:Int):Images

    @Query("UPDATE person_images SET image = :imageUri WHERE person_id = :person_id")
    suspend fun updateImage(person_id: Int , imageUri:String)

}