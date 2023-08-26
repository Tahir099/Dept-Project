package com.example.borcdefteri.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.borcdefteri.R
import com.example.borcdefteri.view.ChooseDialog

@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(labelValue:String , keyboardOptions: KeyboardOptions = KeyboardOptions.Default, isNumberInput: Boolean = false, onValueChange: (String) -> Unit) {
    val keyboardType = if (isNumberInput) KeyboardType.Number else keyboardOptions.keyboardType
    val textValue = remember{ mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions.copy(keyboardType = keyboardType),
        label = { Text(text = labelValue) },
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onValueChange(it)
        }
    )
}
@Composable
fun ButtonComponent(value:String , onClick:() -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        onClick = onClick,
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = value ,
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
@Composable
fun ProfileImage(setUri:(Uri)->Unit) {
    val showDialog = remember { mutableStateOf(false) }
    val imageUri = rememberSaveable{ mutableStateOf("") }
    val painter = rememberImagePainter(
        if(imageUri.value.isEmpty())
            R.drawable.ic_user
        else
            imageUri.value
    )
    if(showDialog.value)
        ChooseDialog(setShowDialog = {showDialog.value = it}){ uri ->
            imageUri.value = uri.toString()
            setUri(uri)
        }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Card(shape = CircleShape , modifier = Modifier
            .padding(8.dp)
            .size(100.dp)
            .clickable {
                showDialog.value = true
            }) {
            Image(painter = painter, contentDescription = null ,
                Modifier
                    .wrapContentSize(),
                contentScale = ContentScale.Crop)
        }
    }
}