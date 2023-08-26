package com.example.borcdefteri.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import com.example.borcdefteri.R
import com.example.borcdefteri.utils.createImageFile
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChooseDialog(setShowDialog:(Boolean) ->Unit , setImageUri: (Uri) -> Unit){
    val context = LocalContext.current
    val imageUri = rememberSaveable{ mutableStateOf("") }
    val painter = rememberImagePainter(
        if(imageUri.value.isEmpty())
            R.drawable.ic_user
        else
            imageUri.value
    )
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider",file
    )
    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){
            uri: Uri? ->
        uri?.let { imageUri.value = it.toString()}
        if(uri != null){
            setImageUri(uri)
        }
        setShowDialog(false)
    }
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                capturedImageUri = uri
                imageUri.value = capturedImageUri.toString()
                setImageUri(uri!!)
                setShowDialog(false)
            }
        }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
        if(it)
        {
            Toast.makeText(context , "Permission granted" , Toast.LENGTH_LONG).show()
            cameraLauncher.launch(uri)
        }
        else
        {
            Toast.makeText(context , "permission denied" , Toast.LENGTH_LONG).show()
        }
    }
    Dialog(onDismissRequest = {
        setShowDialog(false)
    }) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Surface(shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), Arrangement.End) {
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
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_camera),
                            contentDescription = null, Modifier.size(30.dp)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = {
                            val permissionCheckResult = ContextCompat.checkSelfPermission(context , Manifest.permission.CAMERA)

                            if(permissionCheckResult == PackageManager.PERMISSION_GRANTED){
                                cameraLauncher.launch(uri)
                            }
                            else{
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }) {
                            Text(text = "Camera")
                        }
                        TextButton(onClick = {
                            launcher.launch("image/*")
                            //setShowDialog(false)
                        }) {
                            Text(text = "Gallery")
                        }
                    }
                }
            }
        }
    }
}
