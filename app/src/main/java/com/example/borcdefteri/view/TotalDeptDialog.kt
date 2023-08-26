package com.example.borcdefteri.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.borcdefteri.R

@Composable
fun totalDeptDialog(setShowDialog:(Boolean) -> Unit , dept:Int) {
    Dialog(onDismissRequest = { setShowDialog(false)}) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = colorResource(id = R.color.pearly_city)
        ) {
            Column{
                Row(modifier = Modifier.fillMaxWidth() , Arrangement.End) {
                    IconButton(onClick = { setShowDialog(false) }) {
                        Icon(painter = painterResource(id = R.drawable.ic_close), contentDescription = "")
                    }
                }
                Row(modifier = Modifier.fillMaxWidth() , Arrangement.Center) {
                    Text(text = "Ümumi borc:${dept}")
                }
                Spacer(modifier = Modifier.height(35.dp))
            }

        }
    }
}