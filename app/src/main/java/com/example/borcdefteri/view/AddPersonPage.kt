package com.example.borcdefteri.view

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.borcdefteri.R
import com.example.borcdefteri.components.ButtonComponent
import com.example.borcdefteri.components.MyTextField
import com.example.borcdefteri.components.NormalTextComponent
import com.example.borcdefteri.components.ProfileImage
import com.example.borcdefteri.utils.saveImageToInternalStorage
import com.example.borcdefteri.viewmodel.AddPersonPageViewModel
import com.example.borcdefteri.viewmodelfactory.AddPersonPageViewModelFactory
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddPersonPage(navController: NavController){

    val coroutineScope = rememberCoroutineScope()
    val tfName = remember { mutableStateOf("") }
    val tfAddress = remember { mutableStateOf("") }
    val tfTel = remember { mutableStateOf("") }
    val tfNotes = remember { mutableStateOf("") }
    val context = LocalContext.current
    var selectedImageUri: Uri? by remember { mutableStateOf(null) }
    val viewModel:AddPersonPageViewModel = viewModel(
        factory = AddPersonPageViewModelFactory(context.applicationContext as Application)
    )
    Column(Modifier.fillMaxSize() , horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .padding(top = 15.dp)) {
            Box(modifier = Modifier.fillMaxWidth()){
                Row(Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Start , verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        navController.navigate("main_page")
                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = null , Modifier.size(20.dp))
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 13.dp), horizontalArrangement = Arrangement.Center , verticalAlignment = Alignment.CenterVertically) {
                    NormalTextComponent(value = stringResource(id = R.string.add_page))
                }
            }

        }
        ProfileImage { selectedUri ->
            selectedImageUri = selectedUri
        }

        Column(Modifier.padding(horizontal = 15.dp)) {
            MyTextField(stringResource(id = R.string.name) , onValueChange = {tfName.value = it})
            MyTextField(stringResource(id = R.string.address) , onValueChange = {tfAddress.value = it})
            MyTextField(stringResource(id = R.string.tel) , isNumberInput = true , onValueChange = {tfTel.value = it})
            MyTextField(stringResource(id = R.string.note) , onValueChange = {tfNotes.value = it})
            Spacer(modifier = Modifier.height(25.dp))

            ButtonComponent(value = "Əlavə et"){
                val person_name = tfName.value
                val person_tel = tfTel.value
                val person_address = tfAddress.value
                val person_notes = tfNotes.value
                if(selectedImageUri != null){
                    val savedUri = saveImageToInternalStorage(context , selectedImageUri!!)
                    coroutineScope.launch {
                        val savedUri = saveImageToInternalStorage(context , selectedImageUri!!)
                        viewModel.addWithImage(person_name , person_tel , person_address , person_notes , savedUri.toString())
                    }

                }
                else{
                    coroutineScope.launch {
                        viewModel.add(person_name , person_tel , person_address , person_notes)
                    }
                }
            }
        }
    }
}




