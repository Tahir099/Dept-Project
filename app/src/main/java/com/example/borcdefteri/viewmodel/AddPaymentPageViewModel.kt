package com.example.borcdefteri.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.borcdefteri.repo.PaymentDaoRepo

class AddPaymentPageViewModel(application: Application) : AndroidViewModel(application){
    val prepo = PaymentDaoRepo(application)

    fun add(personId:Int , amount:Int , date:String){
        prepo.addPayment(personId , amount , date)
    }
}