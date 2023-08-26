package com.example.borcdefteri.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.borcdefteri.entity.Dept
import com.example.borcdefteri.entity.DeptWithPerson
import com.example.borcdefteri.room.DataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DeptDaoRepo(application: Application){
    var deptList = MutableLiveData<List<DeptWithPerson>>()
    var db:DataBase
    var totalDeptById = MutableLiveData<Int>()
    var totalDept = MutableLiveData<Int>()
    init{
       db = DataBase.DataBaseAccess(application)!!
    }

    fun addDept(person_id: Int , date:String , procedure:String , note:String , cost:Int){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            val dept = Dept(0 , person_id , date , procedure , note , cost)
            db.DeptDao().addDept(dept)
            getAllDeptsById(person_id)
        }
    }

    fun getTotalDept(){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            totalDept.value = db.DeptDao().getTotalDept()
        }
    }
    fun totalDeptById(): MutableLiveData<Int>{
        return totalDeptById
    }
    fun getTotalDeptById(person_id:Int){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            totalDeptById.value = db.DeptDao().getTotalDeptById(person_id)
        }
    }
    fun getAllDeptsById(person_id:Int){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            deptList.value = db.DeptDao().getAllDeptById(person_id)
        }
    }

    fun getDeptsById():MutableLiveData<List<DeptWithPerson>>{
        return deptList
    }
    fun totalDept() : MutableLiveData<Int>{
        return totalDept
    }

}