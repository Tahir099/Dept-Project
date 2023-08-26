package com.example.borcdefteri.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.borcdefteri.entity.PersonWithImage
import com.example.borcdefteri.repo.DeptDaoRepo
import com.example.borcdefteri.repo.PaymentDaoRepo
import com.example.borcdefteri.repo.PersonDaoRepo

class MainPageViewModel(application: Application) :AndroidViewModel(application){
    var prepo = PersonDaoRepo(application)
    var deptrepo = DeptDaoRepo(application)
    var paymentrepo = PaymentDaoRepo(application)
    var personList = MutableLiveData<List<PersonWithImage>>()
    var totalDept = MutableLiveData<Int>()
    var totalPayment= MutableLiveData<Int>()

    init{
        getPerson()
        totalPayment = paymentrepo.takeTotalPayment()
        totalDept = deptrepo.totalDept
        personList = prepo.getPersons()
    }
    fun getPerson(){
        prepo.getAllPerson()
    }
    fun search(word:String){
        prepo.personSearch(word)
    }
    private val dropdownStateMap = mutableMapOf<Int, MutableState<Boolean>>()
    fun getDropdownState(personId:Int):MutableState<Boolean>{
        if (!dropdownStateMap.containsKey(personId)) {
            dropdownStateMap[personId] = mutableStateOf(false)
        }
        return dropdownStateMap[personId]!!
    }
    fun delete(personId: Int){
        prepo.deletePersons(personId)
    }
    fun getTotalDept(){
        deptrepo.getTotalDept()
    }
    fun getTotalPayment(){
        paymentrepo.getTotalPayment()
    }
}