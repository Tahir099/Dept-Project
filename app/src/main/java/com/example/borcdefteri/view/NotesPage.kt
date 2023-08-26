package com.example.borcdefteri.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.borcdefteri.viewmodel.SharedViewModel

@Composable
fun NotesPage() {
    val sharedViewModel:SharedViewModel = viewModel()
    val text = remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = true){
        text.value = sharedViewModel.notes.value?:""
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp).padding(top = 10.dp)){
        Text(text = text.value, style = TextStyle(
            fontSize = 20.sp
        ))
    }
}