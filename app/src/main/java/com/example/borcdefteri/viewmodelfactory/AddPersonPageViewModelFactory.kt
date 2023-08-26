package com.example.borcdefteri.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.borcdefteri.viewmodel.AddPersonPageViewModel

class AddPersonPageViewModelFactory(var application: Application) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddPersonPageViewModel(application) as T
    }
}