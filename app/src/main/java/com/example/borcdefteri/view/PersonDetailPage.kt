package com.example.borcdefteri.view
import PersonDetailPageViewModelFactory
import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.borcdefteri.entity.Person
import com.example.borcdefteri.viewmodel.DetailPageViewModel
import com.example.borcdefteri.viewmodel.SharedViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailPage(navController: NavController , person: Person) {
    val items = listOf("Detallar" , "Əlavə et" , "Qeyd")
    val selectedItem = remember { mutableStateOf(0) }
    val context = LocalContext.current
    val viewModel:DetailPageViewModel = viewModel(
        factory = PersonDetailPageViewModelFactory(context.applicationContext as Application)
    )
    val totalPayment by viewModel.totalPaymentById.observeAsState(0)
    val totalDept by viewModel.totalDeptById.observeAsState(0)
    val deptList by viewModel.deptList.observeAsState(listOf())
    val paymentList by viewModel.paymentList.observeAsState(listOf())
    val sharedViewModel: SharedViewModel = viewModel()
    val image = viewModel.image.observeAsState()
    val imagaUri = image.value?.image
    val painter = rememberImagePainter(
        if(imagaUri.isNullOrEmpty())
            R.drawable.ic_user
        else
            imagaUri
    )
    val showDialog = remember { mutableStateOf(false) }
    val isSelected = remember { mutableStateOf(true) }
    val calculatedDifference = (totalDept?:0) - (totalPayment?:0)
    LaunchedEffect(key1 = true){
        viewModel.getImageById(person.person_id)
        sharedViewModel.selectedPersonId.value = person.person_id
        sharedViewModel.notes.value = person.person_notes
        viewModel.getDeptById(person.person_id)
        viewModel.getPaymentById(person.person_id)
        viewModel.getTotalDeptById(person.person_id)
        viewModel.getTotalPaymentById(person.person_id)
    }
    if(showDialog.value)
        CustomDialog(setShowDialog = {
            showDialog.value = it
        }){

        }
    Scaffold(
        content = {
            when(selectedItem.value){
                0->{
                    Box{
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(colorResource(id = R.color.andrea_blue)),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 30.dp)
                                    .padding(horizontal = 5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(onClick = {
                                    navController.navigate("main_page")
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_back),
                                        tint = Color.White,
                                        contentDescription = ""
                                    )
                                }
                                Text(
                                    text = "Detal səhifəsi", style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(id = R.color.white)
                                    )
                                )
                                TextButton(onClick = {
                                    showDialog.value = true
                                }) {
                                    Text(
                                        text = "Ödə", style = TextStyle(
                                            color = Color.White,
                                            fontSize = 14.sp
                                        )
                                    )
                                }
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp) , horizontalArrangement = Arrangement.Start , verticalAlignment = Alignment.CenterVertically) {
                                Card(shape = CircleShape , modifier = Modifier
                                    .padding(8.dp)
                                    .size(105.dp)
                                ) {
                                    Image(painter = painter, contentDescription = null ,
                                        Modifier
                                            .wrapContentSize(),
                                        contentScale = ContentScale.Crop)
                                }
                                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                                    Text(text ="Ad:${person.person_name}" , style = TextStyle(color = Color.White , fontSize = 14.sp))
                                    Box(modifier = Modifier.size(5.dp))
                                    Text(text = "Adres:${person.person_address}"  , style = TextStyle(color = Color.White , fontSize = 14.sp))
                                    Box(modifier = Modifier.size(5.dp))
                                    Text(text = "Telefon:${person.person_tel}"  , style = TextStyle(color = Color.White , fontSize = 14.sp))
                                    Box(modifier = Modifier.size(5.dp))
                                    Text(text = "Borc:$calculatedDifference"   , style = TextStyle(color = Color.White , fontSize = 14.sp))
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 220.dp)
                    ){
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(colorResource(id = R.color.andrea_blue))
                                .clip(RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
                        ) {
                            LazyColumn(modifier = Modifier.padding(top = 18.dp ,bottom = 80.dp)){
                                if(isSelected.value){
                                    items(
                                        count = deptList.count(),
                                        itemContent = {
                                            val dept = deptList[it]
                                            Card(
                                                modifier = Modifier
                                                    .padding(0.dp)
                                                    .padding(horizontal = 4.dp)
                                                    .fillMaxWidth()
                                            ) {
                                                Row(
                                                    Modifier
                                                        .clickable {
                                                        }
                                                        .fillMaxWidth()
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .padding(5.dp)
                                                            .fillMaxWidth(),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        Column {
                                                            Text(text = "${dept.dept.procedure}   ${dept.dept.cost} AZN")
                                                            Text(text = dept.dept.note , style = TextStyle(
                                                                fontSize = 13.sp
                                                            ))
                                                        }
                                                        Text(dept.dept.date)
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }
                                else{
                                    items(
                                        count = paymentList.count(),
                                        itemContent = {
                                            val payment = paymentList[it]
                                            Card(
                                                modifier = Modifier
                                                    .padding(0.dp)
                                                    .padding(horizontal = 4.dp)
                                                    .fillMaxWidth()
                                            ) {
                                                Row(
                                                    Modifier
                                                        .clickable {
                                                        }
                                                        .fillMaxWidth()
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .padding(5.dp)
                                                            .fillMaxWidth(),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        Text(text = "${payment.payment.amount} AZN")
                                                        Text(payment.payment.date)
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Box(
                        Modifier
                            .padding(top = 205.dp)
                            .fillMaxWidth()
                    ){
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                Modifier
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(colorResource(id = R.color.pearly_city)),
                                Arrangement.Center
                            ) {
                                TextButton(onClick = { isSelected.value = true }
                                ) {
                                    Text(
                                        text = "Borc",
                                        color = if (isSelected.value) Color.Black else Color.White,
                                        fontSize = if (isSelected.value) 16.sp else 14.sp
                                    )
                                }
                                TextButton(onClick = { isSelected.value = false }) {
                                    Text(
                                        text = "Ödəniş",
                                        color = if (!isSelected.value) Color.Black else Color.White,
                                        fontSize = if (!isSelected.value) 16.sp else 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
                1->{
                    AddDeptPage()
                }
                2->{
                    NotesPage()
                }
            }
        },
        bottomBar = {
            NavigationBar(){
                items.forEachIndexed{index, item ->
                    NavigationBarItem(
                        selected = selectedItem.value == index,
                        onClick = { selectedItem.value = index},
                        label = { Text(text = item) },
                        icon = {
                            when(item){
                                "Detallar" -> Icon(painter = painterResource(id = R.drawable.details_img), contentDescription = "" , modifier = Modifier.size(20.dp))
                                "Əlavə et" -> Icon(painter = painterResource(id = R.drawable.ic_add ), contentDescription = "")
                                "Qeyd" -> Icon(painter = painterResource(id = R.drawable.ic_note ), contentDescription = "")
                            }
                        }
                    )
                }
            }
        }
    )

}
