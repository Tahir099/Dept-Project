package com.example.borcdefteri

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.borcdefteri.entity.Person
import com.example.borcdefteri.ui.theme.BorcDefteriTheme
import com.example.borcdefteri.view.AddPersonPage
import com.example.borcdefteri.view.MainPage
import com.example.borcdefteri.view.PersonDetailPage
import com.example.borcdefteri.view.UpdatePersonPage
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WindowCompat.setDecorFitsSystemWindows(
                window,
                false
            )
            BorcDefteriTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigations()
                }
            }
        }
    }
}

@Composable
fun Navigations() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_page"){
        composable("main_page"){
            MainPage(navController = navController)
        }
        composable("add_person_page"){
            AddPersonPage(navController = navController)
        }
        composable("person_detail_page/{person}" , arguments = listOf(
            navArgument("person"){ NavType.StringType}
        )){
            val personJson = it.arguments?.getString("person")
            val person = Gson().fromJson(personJson , Person::class.java)
            PersonDetailPage(navController = navController, person)
        }
        composable("update_person_page/{person}" , arguments = listOf(
            navArgument("person"){NavType.StringType}
        )){
            val personJson = it.arguments?.getString("person")
            val person = Gson().fromJson(personJson , Person::class.java)
            UpdatePersonPage(navController , person)
        }

    }
}