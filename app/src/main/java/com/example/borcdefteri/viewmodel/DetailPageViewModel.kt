package com.example.borcdefteri.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.borcdefteri.entity.DeptWithPerson
import com.example.borcdefteri.entity.Images
import com.example.borcdefteri.entity.PaymentWithPerson
import com.example.borcdefteri.repo.DeptDaoRepo
import com.example.borcdefteri.repo.ImagesDaoRepo
import com.example.borcdefteri.repo.PaymentDaoRepo

class DetailPageViewModel(application: Application) :AndroidViewModel(application){
    var drepo = DeptDaoRepo(application)
    var prepo = PaymentDaoRepo(application)
    var irepo = ImagesDaoRepo(application)
    var deptList = MutableLiveData<List<DeptWithPerson>>()
    var paymentList = MutableLiveData<List<PaymentWithPerson>>()
    var image = MutableLiveData<Images>()
    var totalDeptById = MutableLiveData(0)
    var totalPaymentById = MutableLiveData(0)

    init {
        image = irepo.getImageLiveData()
        deptList = drepo.getDeptsById()
        paymentList = prepo.getPaymentsById()
        totalDeptById = drepo.totalDeptById()
        totalPaymentById = prepo.taketotalPaymentById()
    }

    fun getImageById(person_id:Int){
        irepo.getImageById(person_id)
    }
    fun getDeptById(person_id:Int){
        drepo.getAllDeptsById(person_id)
    }
    fun getTotalDeptById(person_id:Int){
        drepo.getTotalDeptById(person_id)
    }
    fun getTotalPaymentById(person_id:Int){
        prepo.getTotalPaymentById(person_id)
    }
    fun getPaymentById(person_id:Int){
        prepo.getAllPaymentsById(person_id)
    }
}