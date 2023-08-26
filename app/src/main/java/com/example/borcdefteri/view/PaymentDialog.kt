package com.example.borcdefteri.view

import PersonDetailPageViewModelFactory
import android.annotation.SuppressLint
import android.app.Application
import android.icu.util.Calendar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.borcdefteri.R
import com.example.borcdefteri.viewmodel.AddPaymentPageViewModel
import com.example.borcdefteri.viewmodel.DetailPageViewModel
import com.example.borcdefteri.viewmodel.SharedViewModel
import com.example.borcdefteri.viewmodelfactory.AddPaymentPageViewModelFactory
import java.text.DateFormat

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(setShowDialog:(Boolean) -> Unit , setValue: (String) -> Unit) {
    val tfError = remember { mutableStateOf("") }
    val tf  = remember { mutableStateOf("") }
    val context = LocalContext.current
    val personIdState = remember { mutableStateOf(0) }
    val sharedViewModel:SharedViewModel = viewModel()
    val viewModel:AddPaymentPageViewModel = viewModel(
        factory = AddPaymentPageViewModelFactory(context.applicationContext as Application)
    )
    val detailPageViewModel:DetailPageViewModel = viewModel(
        factory = PersonDetailPageViewModelFactory(context.applicationContext as Application)
    )
    LaunchedEffect(key1 = true){
        personIdState.value = sharedViewModel.selectedPersonId.value?:0
    }
    Dialog(
        onDismissRequest = {setShowDialog(false)}
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(contentAlignment = Alignment.Center){
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Məbləğ",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            painterResource(id = R.drawable.ic_close),
                            contentDescription = "",
                            tint = Color.Gray,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = if (tfError.value.isEmpty()) Color.Black else Color.Red
                                ),
                                shape = RoundedCornerShape(50)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text(text = "Məbləğ")},
                        value = tf.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            tf.value = it.take(10)
                        }
                    )
                    Spacer(modifier = Modifier.height(25.dp))

                    Box(modifier = Modifier.padding(40.dp , 0.dp , 40.dp , 0.dp)){
                        Button(onClick = {
                            if(tf.value.isEmpty()){
                                tfError.value = "Field can not be empty"
                                return@Button
                            }
                            val calendar = Calendar.getInstance().time
                            val dateFormat = DateFormat.getDateInstance().format(calendar)
                            setValue(tf.value)
                            viewModel.prepo.addPayment(personIdState.value , tf.value.toInt() , dateFormat)
                            setShowDialog(false)
                            detailPageViewModel.getPaymentById(personIdState.value)
                            detailPageViewModel.getTotalDeptById(personIdState.value)
                            detailPageViewModel.getTotalPaymentById(personIdState.value)
                        },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "OK")
                        }
                    }
                }
            }
        }
    }

}