package com.example.semestralka_fitnessapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.semestralka_fitnessapp.screens.MenuScreen
import com.example.semestralka_fitnessapp.screens.ClassicWorkoutScreen
import com.example.semestralka_fitnessapp.screens.CongratsScreen
import com.example.semestralka_fitnessapp.screens.CustomWorkoutScreen
import com.example.semestralka_fitnessapp.screens.WorkoutSelectionScreen
import com.example.semestralka_fitnessapp.repository.CustomWorkoutRepository
import com.example.semestralka_fitnessapp.repository.CvikRepository
import com.example.semestralka_fitnessapp.repository.StatisticsRepository
import com.example.semestralka_fitnessapp.ui.screens.StatisticsScreen
import com.example.semestralka_fitnessapp.viewModel.CvikViewModel
import com.example.semestralka_fitnessapp.viewModel.CvikViewModelFactory
import com.example.semestralka_fitnessapp.viewModel.CustomWorkoutViewModel
import com.example.semestralka_fitnessapp.viewModel.CustomWorkoutViewModelFactory
import com.example.semestralka_fitnessapp.viewModel.StatisticsViewModel
import com.example.semestralka_fitnessapp.viewModel.StatisticsViewModelFactory

@Composable
fun AppNavGraph(
    navController: NavHostController,
    cvikViewModelFactory: CvikViewModelFactory,
    cvikRepository: CvikRepository,
    statisticsRepository: StatisticsRepository,
    customWorkoutRepository: CustomWorkoutRepository
){
    val cvikViewModel: CvikViewModel = viewModel(
        factory = cvikViewModelFactory)
    val statisticsViewModel: StatisticsViewModel = viewModel(
        factory = StatisticsViewModelFactory(statisticsRepository)
    )
    val customWorkoutViewModel: CustomWorkoutViewModel = viewModel(
        factory = CustomWorkoutViewModelFactory(cvikRepository, customWorkoutRepository)
    )

    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            MenuScreen(navController)
        }
        composable("classicWorkout") {
            val cvikViewModel: CvikViewModel = viewModel(
                key = "classicWorkout",
                factory = CvikViewModelFactory(
                    repositoryClassic = cvikRepository,
                    repositoryCustom = customWorkoutRepository,
                    statisticsRepository = statisticsRepository,
                    jeKlasicky = true
                )
            )
            ClassicWorkoutScreen(
                navController = navController,
                viewModel = cvikViewModel
            )
        }
        composable("customWorkout") {
            CustomWorkoutScreen(
                navController = navController,
                viewModel = customWorkoutViewModel
            )
        }
        composable("customWorkoutPlay") {
            WorkoutSelectionScreen(
                navController = navController,
                customWorkoutViewModel = customWorkoutViewModel
            )
        }
        composable("playCustomWorkout/{id}") { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0

            val cvikViewModel: CvikViewModel = viewModel(
                key = "customWorkout_$workoutId",
                factory = CvikViewModelFactory(
                    repositoryClassic = cvikRepository,
                    repositoryCustom = customWorkoutRepository,
                    statisticsRepository = statisticsRepository,
                    jeKlasicky = false,
                    customWorkoutId = workoutId
                )
            )

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

        composable("challengeWorkout") {
            val vyzvaViewModel: CvikViewModel = viewModel(
                key = "vyzvaViewModel",
                factory = CvikViewModelFactory(
                    repositoryClassic = cvikRepository,
                    repositoryCustom = customWorkoutRepository,
                    statisticsRepository = statisticsRepository,
                    jeKlasicky = false, // nie je klasický
                    jeVyzva = true      // je výzva
                )
            )

            ClassicWorkoutScreen( // môžeš použiť rovnakú obrazovku ako pre klasický tréning
                navController = navController,
                viewModel = vyzvaViewModel
            )
        }

    }
}
