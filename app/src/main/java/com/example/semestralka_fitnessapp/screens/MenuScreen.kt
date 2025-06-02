package com.example.semestralka_fitnessapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.semestralka_fitnessapp.R

@Composable
fun MenuScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.menu_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Easy Fitness+",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navController.navigate("classicWorkout") }, modifier = Modifier.fillMaxWidth()) {
                Text("Preddefinovaný tréning")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("customWorkout") }, modifier = Modifier.fillMaxWidth()) {
                Text("Vytvor si vlastný tréning")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("customWorkoutPlay") }, modifier = Modifier.fillMaxWidth()) {
                Text("Spusti vlastný tréning")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("challengeWorkout") }, modifier = Modifier.fillMaxWidth()) {
                Text("Výzvy")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("statistics") }, modifier = Modifier.fillMaxWidth()) {
                Text("Štatistiky")
            }
        }
    }
}
