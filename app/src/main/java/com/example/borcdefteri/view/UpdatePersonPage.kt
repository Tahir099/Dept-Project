package com.example.borcdefteri.view

import android.app.Application
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.borcdefteri.R
import com.example.borcdefteri.components.ButtonComponent
import com.example.borcdefteri.components.MyTextField
import com.example.borcdefteri.components.NormalTextComponent
import com.example.borcdefteri.entity.Person
import com.example.borcdefteri.utils.saveImageToInternalStorage
import com.example.borcdefteri.utils.updateImage
import com.example.borcdefteri.viewmodel.UpdatePersonPageViewModel
import com.example.borcdefteri.viewmodelfactory.UpdatePersonPageViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePersonPage(navController: NavController , person:Person) {
    val tfName = remember { mutableStateOf("") }
    val tfAddress = remember { mutableStateOf("") }
    val tfTel = remember { mutableStateOf("") }
    val tfNotes = remember { mutableStateOf("") }
    val context = LocalContext.current
    var selectedImageUri: Uri? by remember { mutableStateOf(null) }
    var oldImageUri:Uri? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()
    val viewModel:UpdatePersonPageViewModel = viewModel(
        factory = UpdatePersonPageViewModelFactory(context.applicationContext as Application)
    )
    val image = viewModel.image.observeAsState()
    val imageUri = image.value?.image
    LaunchedEffect(key1 = imageUri) {
        if (!imageUri.isNullOrEmpty()) {
            selectedImageUri = Uri.parse(imageUri)
            oldImageUri = selectedImageUri
        }
    }
    val painter = rememberImagePainter(
        if(selectedImageUri == null)
            R.drawable.ic_user
        else
            selectedImageUri
    )
    val showDialog = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true){
        tfName.value = person.person_name
        tfAddress.value = person.person_address
        tfTel.value = person.person_tel
        tfNotes.value = person.person_notes
        viewModel.getImageById(person.person_id)
    }
    if(showDialog.value){
        ChooseDialog(setShowDialog = {showDialog.value = it}, setImageUri = {uri->
            selectedImageUri = uri
        })
    }
    Column(Modifier.fillMaxSize() , horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .padding(top = 5.dp)) {
            Box(modifier = Modifier.fillMaxWidth()){
                Row(Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Start , verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        navController.navigate("main_page")
                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = null , Modifier.size(18.dp))
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 13.dp), horizontalArrangement = Arrangement.Center , verticalAlignment = Alignment.CenterVertically) {
                    NormalTextComponent(value = "Düzəliş et")
                }
            }

        }
        Card(shape = CircleShape , modifier = Modifier
            .clickable {
                showDialog.value = true
            }
            .padding(8.dp)
            .size(105.dp)
        ) {
            Image(painter = painter, contentDescription = null ,
                Modifier
                    .wrapContentSize(),
                contentScale = ContentScale.Crop)
        }

        Column(Modifier.padding(horizontal = 15.dp)) {
            OutlinedTextField(value = tfName.value, onValueChange = {tfName.value = it} , Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(value = tfAddress.value, onValueChange = {tfAddress.value = it} , Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(value = tfTel.value, onValueChange = {tfTel.value = it} , Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(value = tfNotes.value, onValueChange = {tfNotes.value = it} , Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(25.dp))

            ButtonComponent(value = "Düzəliş et"){
                val person_name = tfName.value
                val person_tel = tfTel.value
                val person_address = tfAddress.value
                val person_notes = tfNotes.value

                if(oldImageUri == null){
                    if(selectedImageUri == null){
                        coroutineScope.launch {
                            viewModel.update(person.person_id , person_name , person_tel ,person_address ,person_notes)
                        }
                    }
                    else{
                        coroutineScope.launch {
                            val image = saveImageToInternalStorage(context  , selectedImageUri!!)
                            viewModel.updatePersonAddImage(person.person_id, person_name , person_tel , person_address , person_notes , image.toString())
                            oldImageUri = image
                        }
                    }
                }
                else{
                    if(selectedImageUri == oldImageUri){
                        coroutineScope.launch {
                            viewModel.update(person.person_id , person_name , person_tel ,person_address ,person_notes)
                        }
                    }else{
                        coroutineScope.launch {
                            val image = updateImage(context  ,oldImageUri!! ,selectedImageUri!!)
                            viewModel.updateWithImage(person.person_id, person_name , person_tel , person_address , person_notes , image.toString())
                        }
                    }
                }
            }
        }
    }

}
