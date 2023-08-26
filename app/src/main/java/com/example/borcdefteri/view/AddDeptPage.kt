package com.example.borcdefteri.view
import PersonDetailPageViewModelFactory
import android.app.Application
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.borcdefteri.R
import com.example.borcdefteri.components.ButtonComponent
import com.example.borcdefteri.components.MyTextField
import com.example.borcdefteri.components.NormalTextComponent
import com.example.borcdefteri.viewmodel.AddDeptPageViewModel
import com.example.borcdefteri.viewmodel.DetailPageViewModel
import com.example.borcdefteri.viewmodel.SharedViewModel
import com.example.borcdefteri.viewmodelfactory.AddDeptPageViewModelFactory
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.text.DateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDeptPage() {
    val tfProcedure = remember { mutableStateOf("") }
    val tfNote = remember { mutableStateOf("") }
    val tfCost = remember { mutableStateOf("") }
    val context = LocalContext.current
    val personIdState = remember { mutableStateOf(0) }
    val sharedViewModel:SharedViewModel = viewModel()
    val viewModel:AddDeptPageViewModel = viewModel(
        factory = AddDeptPageViewModelFactory(context.applicationContext as Application)
    )
    val detailPageViewModel:DetailPageViewModel = viewModel(
        factory = PersonDetailPageViewModelFactory(context.applicationContext as Application)
    )

    LaunchedEffect(key1 = true){
        personIdState.value = sharedViewModel.selectedPersonId.value?:0
    }
    Box(modifier = Modifier.fillMaxSize()){
        Column(Modifier.fillMaxSize() , verticalArrangement = Arrangement.Top , Alignment.CenterHorizontally) {
            Row(Modifier.padding(top = 60.dp)) {
                Text(text = "Borc" , style = TextStyle(
                    fontSize = 25.sp
                )
                )
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            Modifier
                .fillMaxSize()
                .padding(20.dp)
                .padding(top = 65.dp) , horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Center) {
            MyTextField(labelValue = "Prosedur", onValueChange = {tfProcedure.value = it})
            MyTextField(labelValue = "Qiymət", onValueChange = {tfCost.value = it} , isNumberInput = true)
            MyTextField(labelValue = "Qeyd", onValueChange = {tfNote.value = it})
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent("Əlavə et"){
                val procedure = tfProcedure.value
                val cost = tfCost.value.toInt()
                val not = tfNote.value
                val calendar = Calendar.getInstance().time
                val dateFormat = DateFormat.getDateInstance().format(calendar)
                viewModel.add(personIdState.value , dateFormat , procedure , not , cost)
                detailPageViewModel.getDeptById(personIdState.value)
                detailPageViewModel.getTotalDeptById(personIdState.value)
                detailPageViewModel.getTotalPaymentById(personIdState.value)
            }
            Spacer(modifier = Modifier.height(150.dp))
        }
    }

}