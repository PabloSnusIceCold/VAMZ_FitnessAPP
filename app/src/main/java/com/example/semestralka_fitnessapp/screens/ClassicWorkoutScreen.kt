package com.example.semestralka_fitnessapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.semestralka_fitnessapp.viewModel.CvikViewModel

@Composable
fun ClassicWorkoutScreen(
    navController: NavController,
    viewModel: CvikViewModel = viewModel()
) {
    val cviky = viewModel.cviky.value
    val currentIndex = viewModel.currentIndex
    val remainingTime = viewModel.remainingTime
    val showPredImage = viewModel.showPredImage
    val workoutFinished = viewModel.workoutFinished.value

    val currentCvik = cviky.getOrNull(currentIndex)
    val scrollState = rememberScrollState()


    LaunchedEffect(cviky) {
        if (cviky.isNotEmpty()) {
            viewModel.startWorkout()
        }
    }

    if (workoutFinished) {
        LaunchedEffect(Unit) {
            navController.navigate("congrats/${viewModel.getTotalCalories()}") {
                popUpTo("classicWorkout") { inclusive = true }
            }
        }
    } else {
        currentCvik?.let { cvik ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF004d40))
            ) {
                Button(
                    onClick = {
                        viewModel.endWorkout()
                        navController.navigate("menu")
                    },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .zIndex(1f)
                ) {
                    Text("← Späť", color = Color.White)
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(top = 72.dp, start = 24.dp, end = 24.dp, bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "Aktuálny cvik: ${cvik.nazov}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )

                    Text(
                        text = "Zostávajúci čas: ${remainingTime}s",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Image(
                        painter = painterResource(
                            id = if (showPredImage) cvik.obrazokPred else cvik.obrazokPo
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    LinearProgressIndicator(
                        progress = {
                            if (cvik.trvanieAleboOpakovania > 0)
                                (cvik.trvanieAleboOpakovania - remainingTime).toFloat() / cvik.trvanieAleboOpakovania
                            else 1f
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = { viewModel.resetWorkout() }) {
                            Text("Reštartovať tréning")
                        }

                        Button(onClick = { viewModel.skipCurrentExercise() }) {
                            Text("Preskočiť cvik")
                        }
                    }
                }
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF004d40)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}
