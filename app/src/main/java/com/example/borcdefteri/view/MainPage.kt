package com.example.borcdefteri.view

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.borcdefteri.R
import com.example.borcdefteri.utils.deleteImageFromInternalStorage
import com.example.borcdefteri.viewmodel.MainPageViewModel
import com.example.borcdefteri.viewmodelfactory.MainPageViewModeLFactory
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage(navController: NavController) {
    val tfPerson = remember { mutableStateOf("") }
    val context = LocalContext.current
    val viewModel:MainPageViewModel = viewModel(
        factory = MainPageViewModeLFactory(context.applicationContext as Application)
    )
    val showDialog = remember {
        mutableStateOf(false)
    }
    val totalDept = viewModel.totalDept.observeAsState()
    val totalPayment = viewModel.totalPayment.observeAsState()
    val personList = viewModel.personList.observeAsState(listOf())
    val imageUri = rememberSaveable{ mutableStateOf("") }
    val calculatedDifference = (totalDept.value?:0) - (totalPayment.value?:0)
    LaunchedEffect(key1 = true){
        viewModel.getPerson()
        viewModel.getTotalDept()
        viewModel.getTotalPayment()
    }
    if(showDialog.value){
        totalDeptDialog(setShowDialog = {
            showDialog.value = it
        }, dept = calculatedDifference)
    }
    Surface(color = Color.White) {
        Scaffold(
            content = {
                Column(Modifier.padding(top = 20.dp)) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = tfPerson.value,
                            onValueChange = {
                                tfPerson.value = it
                                viewModel.search(it)
                            },
                            Modifier
                                .fillMaxWidth(0.84f)
                                .padding(20.dp),
                            shape = RoundedCornerShape(30.dp),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_search),
                                    contentDescription = ""
                                )
                            },
                            placeholder = { Text(text = "Axtarış") }
                        )
                        TextButton(onClick = {
                            showDialog.value = true
                        }) {
                            Text(
                                text = "Borc", style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 15.sp,
                                )
                            )
                        }
                    }
                    LazyColumn {

                        items(
                            count = personList.value!!.count()
                        ) {
                            val person = personList.value!![it]
                            val dropDownState = viewModel.getDropdownState(person.person.person_id).value
                            if (person.image != null) {
                                imageUri.value = person.image.image
                            } else
                                imageUri.value = ""
                            val painter = rememberImagePainter(
                                imageUri.value.ifEmpty { R.drawable.ic_user }
                            )
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val personJson = Gson().toJson(person.person)
                                        navController.navigate("person_detail_page/${personJson}")
                                    }


                                    .padding(horizontal = 12.dp)) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        shape = CircleShape, modifier = Modifier
                                            .padding(8.dp)
                                            .size(60.dp)
                                    ) {
                                        Image(
                                            painter = painter,
                                            contentDescription = null,
                                            Modifier.wrapContentSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(verticalArrangement = Arrangement.SpaceBetween) {
                                            Text(
                                                text = person.person.person_name,
                                                style = TextStyle(
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                            Spacer(modifier = Modifier.size(4.dp))
                                            Text(
                                                text = person.person.person_address,
                                                style = TextStyle(
                                                    fontSize = 14.sp,
                                                    color = Color.Gray,
                                                )
                                            )
                                        }
                                        Box {
                                            IconButton(onClick = {
                                                val currentState = dropDownState
                                                viewModel.getDropdownState(person.person.person_id).value = !currentState
                                            }) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_more),
                                                    contentDescription = ""
                                                )
                                            }
                                            if (dropDownState) {
                                                DropdownMenu(
                                                    expanded = true,
                                                    onDismissRequest = {
                                                        viewModel.getDropdownState(
                                                            person.person.person_id
                                                        ).value = false
                                                    }) {
                                                    DropdownMenuItem(
                                                        text = { Text(text = "Sil") },
                                                        onClick = {
                                                            viewModel.delete(person.person.person_id)
                                                            if (person.image != null) {
                                                                val imageUri = Uri.parse(person.image.image)
                                                                deleteImageFromInternalStorage(context, imageUri)
                                                            }
                                                            viewModel.getTotalDept()
                                                            viewModel.getTotalPayment()
                                                        })
                                                    DropdownMenuItem(
                                                        text = { Text(text = "Düzeliş et") },
                                                        onClick = {
                                                            val personJson = Gson().toJson(person.person)
                                                            navController.navigate("update_person_page/${personJson}")
                                                        })
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("add_person_page")
                    }, containerColor = colorResource(id = R.color.palatinate_blue),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_add),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        )
    }

}