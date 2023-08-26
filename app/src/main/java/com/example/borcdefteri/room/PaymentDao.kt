package com.example.borcdefteri.room

import androidx.compose.runtime.Composable
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.borcdefteri.entity.DeptWithPerson
import com.example.borcdefteri.entity.Payment
import com.example.borcdefteri.entity.PaymentWithPerson

@Dao
interface PaymentDao{
    @Query("SELECT * FROM Payments WHERE person_id  = :personId")
    suspend fun paymentById(personId:Int):List<PaymentWithPerson>

    @Query("SELECT SUM(amount) FROM Payments WHERE person_id = :person_id")
    suspend fun  totalPaymentById(person_id:Int):Int

    @Query("SELECT SUM(amount) FROM Payments")
    suspend fun  totalPayment():Int

    @Insert
    suspend fun addPayment(payment: Payment)

}