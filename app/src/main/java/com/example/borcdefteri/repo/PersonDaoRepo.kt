package com.example.borcdefteri.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.borcdefteri.entity.Images
import com.example.borcdefteri.entity.Person
import com.example.borcdefteri.entity.PersonWithImage
import com.example.borcdefteri.room.DataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonDaoRepo(var application: Application) {
    var db:DataBase
    var personList = MutableLiveData<List<PersonWithImage>>()
    init{
        db = DataBase.DataBaseAccess(application)!!
    }

    suspend fun updatePerson(person_id: Int ,personName: String , person_tel: String , person_address: String , person_notes: String){
        val updatedPerson = Person(person_id , personName , person_tel , person_address , person_notes)
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            db.PersonDao().updatePerson(updatedPerson)
        }
    }
    suspend fun updatePersonWithImage(person_id :Int,person_name: String, person_tel: String, person_address: String, person_notes: String , imageUri:String){
        updatePerson(person_id , person_name , person_address ,person_tel , person_notes)
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            db.ImagesDao().updateImage(person_id , imageUri)
        }
    }
    suspend fun updatePersonAddImage(person_id :Int,person_name: String, person_tel: String, person_address: String, person_notes: String , imageUri:String){
        updatePerson(person_id , person_name , person_tel ,person_address , person_notes)
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            val image =Images( 0  ,person_id , imageUri)
            db.ImagesDao().addImage(image)
        }
    }

    suspend fun addPerson(personName: String, person_tel: String, person_address: String, person_notes: String): Long {
        return withContext(Dispatchers.IO) {
            db.PersonDao().addPerson(Person(0, personName, person_tel, person_address, person_notes))
    }
    }

    suspend fun addPersonWithImage(personName: String, person_tel: String, person_address: String, person_notes: String, imageUri: String) {
        val personId = addPerson(personName, person_tel, person_address, person_notes)

        val image = Images(0, personId.toInt(), imageUri)
        withContext(Dispatchers.IO) {
            db.ImagesDao().addImage(image)
        }
    }


    fun getAllPerson(){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            personList.value = db.PersonDao().getPersons()
        }
    }
    fun getPersons():MutableLiveData<List<PersonWithImage>>{
        return personList
    }
    fun personSearch(word:String){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            personList.value = db.PersonDao().searchPerson(word)
        }
    }

    fun deletePersons(person_id:Int){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            val deletedPerson = Person(person_id , "" , "" , "" ,"")
            db.PersonDao().deletePerson(deletedPerson)
            getAllPerson()
        }
    }
}