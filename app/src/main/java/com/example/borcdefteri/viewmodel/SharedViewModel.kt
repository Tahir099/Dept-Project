package com.example.borcdefteri.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class SharedViewModel:ViewModel(){
    val selectedPersonId = MutableStateFlow<Int?>(0)
    val notes = MutableStateFlow<String?>(null)
}