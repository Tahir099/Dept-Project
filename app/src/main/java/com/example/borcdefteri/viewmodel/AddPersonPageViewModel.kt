package com.example.borcdefteri.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.borcdefteri.repo.PersonDaoRepo

class AddPersonPageViewModel(application: Application) :AndroidViewModel(application){
    var prepo = PersonDaoRepo(application)

    suspend fun add(person_name: String, person_tel: String, person_address: String, person_notes: String){
        prepo.addPerson(person_name , person_tel , person_address ,person_notes)
    }
    suspend fun addWithImage(personName: String, person_tel: String, person_address: String, person_notes: String, imageUri: String){
        prepo.addPersonWithImage(personName , person_tel , person_address ,  person_notes ,imageUri)
    }

}