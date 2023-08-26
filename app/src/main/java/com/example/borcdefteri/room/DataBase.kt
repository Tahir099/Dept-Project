package com.example.borcdefteri.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.borcdefteri.entity.Dept
import com.example.borcdefteri.entity.Images
import com.example.borcdefteri.entity.Payment
import com.example.borcdefteri.entity.Person


@Database(entities = [Person::class , Images::class , Dept::class , Payment::class] , version = 2)
abstract class DataBase : RoomDatabase(){
    abstract fun PersonDao():PersonDao
    abstract fun ImagesDao():ImagesDao
    abstract fun DeptDao():DeptDao
    abstract fun PaymentDao():PaymentDao
    companion object{
        var INSTANCE:DataBase? = null

        fun DataBaseAccess(context: Context):DataBase?{
            if(INSTANCE == null){
                synchronized(DataBase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        "Dept.db"
                    ).createFromAsset("Dept.db").build()
                }
            }

            return INSTANCE
        }
    }
}