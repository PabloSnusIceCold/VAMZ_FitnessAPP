package com.example.semestralka_fitnessapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.semestralka_fitnessapp.MenuScreen
import com.example.semestralka_fitnessapp.ClassicWorkoutScreen
import com.example.semestralka_fitnessapp.CongratsScreen
import com.example.semestralka_fitnessapp.repository.StatisticsRepository
import com.example.semestralka_fitnessapp.ui.screens.StatisticsScreen
import com.example.semestralka_fitnessapp.viewModel.CvikViewModel
import com.example.semestralka_fitnessapp.viewModel.CvikViewModelFactory
import com.example.semestralka_fitnessapp.viewModel.StatisticsViewModel
import com.example.semestralka_fitnessapp.viewModel.StatisticsViewModelFactory

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModelFactory: CvikViewModelFactory,
    statisticsRepository: StatisticsRepository // potrebujeme repozitár pre statistics VM
) {
    val cvikViewModel: CvikViewModel = viewModel(factory = viewModelFactory)
    val statisticsViewModel: StatisticsViewModel = viewModel(
        factory = StatisticsViewModelFactory(statisticsRepository)
    )

    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            MenuScreen(navController)
        }
        composable("classicWorkout") {
            ClassicWorkoutScreen(
                navController = navController,
                viewModel = cvikViewModel
            )
        }
        composable("statistics") {
            StatisticsScreen(
                navController = navController,
                viewModel = statisticsViewModel
            )
        }
        composable("congrats/{calories}") { backStackEntry ->
            val calories = backStackEntry.arguments?.getString("calories")?.toIntOrNull() ?: 0
            CongratsScreen(
                calories = calories,
                onOkClicked = { navController.navigate("menu") }
            )
        }
    }
}

