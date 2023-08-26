package com.example.borcdefteri.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("Recycle")
fun saveImageToInternalStorage(context: Context, imageUri: Uri): Uri {
    val inputStream = context.contentResolver.openInputStream(imageUri)
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_$timeStamp.jpg"

    val imagesDir = File(context.filesDir, "images")
    imagesDir.mkdirs()
    val imageFile = File(imagesDir, imageFileName)

    val outputStream = FileOutputStream(imageFile)

    outputStream.use { output ->
        inputStream?.copyTo(output)
    }

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        imageFile
    )
}

fun deleteImageFromInternalStorage(context: Context, imageUri: Uri) {
    val imageFile = imageUri.path?.let { File(it) }
    if (imageFile!!.exists()) {
        imageFile.delete()
        context.contentResolver.delete(imageUri, null, null)
    }
}

fun updateImage(context: Context , imageUri: Uri ,  newImageUri: Uri): Uri{
    deleteImageFromInternalStorage(context , imageUri)
    return saveImageToInternalStorage(context , newImageUri)
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_$timeStamp.jpg"
    val imagesDir = File(filesDir, "images")
    imagesDir.mkdirs()
    val image = File(imagesDir, imageFileName)
    return image
}
