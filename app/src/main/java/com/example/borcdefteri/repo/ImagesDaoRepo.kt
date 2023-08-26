package com.example.borcdefteri.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.borcdefteri.entity.Images
import com.example.borcdefteri.room.DataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImagesDaoRepo (var application: Application){
    var db:DataBase

    var image = MutableLiveData<Images>()
    init {
        db = DataBase.DataBaseAccess(application)!!
    }

    fun getImageById(person_id:Int){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            image.value = db.ImagesDao().getImageById(person_id)
        }
    }

    fun getImageLiveData(): MutableLiveData<Images> { // getImage adını getImageLiveData olarak değiştirildi
        return image
    }
}