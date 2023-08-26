package com.example.borcdefteri.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.borcdefteri.entity.Payment
import com.example.borcdefteri.entity.PaymentWithPerson
import com.example.borcdefteri.room.DataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PaymentDaoRepo (application: Application){
    var paymentList = MutableLiveData<List<PaymentWithPerson>>()
    var db:DataBase
    var totalPaymentById = MutableLiveData<Int>()
    var totalPayment = MutableLiveData<Int>()

    init{
        db = DataBase.DataBaseAccess(application)!!
    }

    fun takeTotalPayment():MutableLiveData<Int>{
        return totalPayment
    }
    fun getTotalPayment(){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            totalPayment.value = db.PaymentDao().totalPayment()
        }
    }
    fun addPayment(person_id:Int , amount:Int , date:String){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            val payment = Payment(0 , person_id , amount , date)
            db.PaymentDao().addPayment(payment)
        }
    }

    fun getTotalPaymentById(person_id: Int){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            totalPaymentById.value = db.PaymentDao().totalPaymentById(person_id)
        }
    }
    fun taketotalPaymentById(): MutableLiveData<Int>{
        return totalPaymentById
    }

    fun getAllPaymentsById(person_id:Int){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            paymentList.value = db.PaymentDao().paymentById(person_id)
        }
    }

    fun getPaymentsById():MutableLiveData<List<PaymentWithPerson>>{
        return paymentList
    }
}