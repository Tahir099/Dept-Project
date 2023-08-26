package com.example.borcdefteri.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.borcdefteri.entity.Dept
import com.example.borcdefteri.entity.DeptWithPerson


@Dao
interface DeptDao {
    @Insert
    suspend fun addDept(dept: Dept)

    @Query("SELECT SUM(cost) FROM dept")
    suspend fun getTotalDept():Int

    @Query("SELECT SUM(cost) FROM dept WHERE person_id = :person_id")
    suspend fun getTotalDeptById(person_id:Int):Int

    @Query("SELECT * FROM dept WHERE person_id = :person_id")
    suspend fun getAllDeptById(person_id:Int):List<DeptWithPerson>
}