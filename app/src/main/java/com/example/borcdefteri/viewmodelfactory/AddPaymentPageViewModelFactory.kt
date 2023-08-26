package com.example.borcdefteri.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.borcdefteri.viewmodel.AddPaymentPageViewModel

class AddPaymentPageViewModelFactory(var application: Application) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddPaymentPageViewModel(application) as T
    }
}