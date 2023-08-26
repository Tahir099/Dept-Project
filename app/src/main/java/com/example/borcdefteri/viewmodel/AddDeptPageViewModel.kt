package com.example.borcdefteri.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.borcdefteri.repo.DeptDaoRepo

class AddDeptPageViewModel(application: Application) :AndroidViewModel(application){
    var drepo = DeptDaoRepo(application)

    fun add(person_id:Int , date:String , procedure:String , note:String , cost:Int){
        drepo.addDept(person_id , date , procedure , note , cost)
    }
}