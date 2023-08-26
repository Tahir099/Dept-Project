package com.example.borcdefteri.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.borcdefteri.viewmodel.UpdatePersonPageViewModel

class UpdatePersonPageViewModelFactory(var application: Application) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UpdatePersonPageViewModel(application) as T
    }
}