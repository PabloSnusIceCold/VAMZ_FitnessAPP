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
import androidx.compose.ui.res.stringResource
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
                text = stringResource(id = R.string.menu_title),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navController.navigate("classicWorkout") }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.classic_workout))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("customWorkout") }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.create_custom_workout))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("customWorkoutPlay") }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.start_custom_workout))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("challengeWorkout") }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.challenge_workout))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("statistics") }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.statistics))
            }
        }
    }
}
