package com.example.semestralka_fitnessapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semestralka_fitnessapp.viewModel.CustomWorkoutViewModel

@Composable
fun CustomWorkoutScreen(
    viewModel: CustomWorkoutViewModel = viewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val filteredCviky by viewModel.filteredCviky.collectAsState()
    val selectedCvik by viewModel.selectedCvik.collectAsState()
    val customWorkoutList by viewModel.customWorkoutList.collectAsState()

    Row(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Kategórie
        Column(modifier = Modifier.weight(1f)) {
            Text("Kategórie", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            categories.forEach { category ->
                val isSelected = category == selectedCategory
                Text(
                    text = category,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(if (isSelected) Color.Gray else Color.Transparent)
                        .clickable { viewModel.selectCategory(category) },
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Cviky
        Column(modifier = Modifier.weight(1f)) {
            Text("Cviky", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            filteredCviky.forEach { cvik ->
                val isSelected = cvik == selectedCvik
                Text(
                    text = cvik.nazov,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(if (isSelected) Color.Gray else Color.Transparent)
                        .clickable { viewModel.selectCvik(cvik) },
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Zadanie opakovaní a pridanie
        Column(modifier = Modifier.weight(1f)) {
            Text("Pridaj cvik", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.repetitionsOrDuration,
                onValueChange = { input ->
                    viewModel.repetitionsOrDuration = input.filter { it.isDigit() }
                },
                label = { Text("Opakovania / Sekundy") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.addCvikToWorkout() },
                enabled = selectedCvik != null && viewModel.repetitionsOrDuration.isNotEmpty()
            ) {
                Text("Pridať cvik")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Vlastný tréning:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            customWorkoutList.forEach {
                Text("${it.cvik.nazov} - ${it.repetitionsOrDuration}")
            }
        }
    }
}
