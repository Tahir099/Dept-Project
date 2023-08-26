package com.example.borcdefteri.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.borcdefteri.entity.Images
import com.example.borcdefteri.repo.ImagesDaoRepo
import com.example.borcdefteri.repo.PersonDaoRepo

class UpdatePersonPageViewModel(application: Application) : AndroidViewModel(application){
    var irepo = ImagesDaoRepo(application)
    var image = MutableLiveData<Images>()
    var prepo = PersonDaoRepo(application)
    init {
        image = irepo.getImageLiveData()
    }
    fun getImageById(person_id:Int){
        irepo.getImageById(person_id)
    }

    suspend fun update(person_id:Int , person_name: String , person_tel:String , person_address: String , person_notes: String){
        prepo.updatePerson(person_id,person_name,person_tel,person_address ,person_notes)
    }
    suspend fun updateWithImage(person_id:Int , person_name:String , person_tel:String , person_address: String ,  person_notes: String , image_uri :String){
        prepo.updatePersonWithImage(person_id , person_name , person_tel , person_address , person_notes , image_uri)
    }
    suspend fun updatePersonAddImage(person_id:Int , person_name:String , person_tel:String , person_address: String ,  person_notes: String , image_uri :String){
        prepo.updatePersonAddImage(person_id , person_name , person_tel , person_address , person_notes , image_uri)
    }
}