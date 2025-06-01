package com.example.semestralka_fitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.semestralka_fitnessapp.data.AppDatabase
import com.example.semestralka_fitnessapp.data.Cvik
import com.example.semestralka_fitnessapp.navigation.AppNavGraph
import com.example.semestralka_fitnessapp.repository.CvikRepository
import com.example.semestralka_fitnessapp.repository.StatisticsRepository
import com.example.semestralka_fitnessapp.viewModel.CvikViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase
    private lateinit var cvikRepository: CvikRepository
    private lateinit var statisticsRepository: StatisticsRepository
    private lateinit var viewModelFactory: CvikViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "fitness-db"
        )
            .fallbackToDestructiveMigration()
            .build()

        // Inicializuj repozitáre
        cvikRepository = CvikRepository(database.cvikDao())
        statisticsRepository = StatisticsRepository(database.statisticsDao()) // ← toto je nové

        // Inicializuj factory s oboma repozitármi
        viewModelFactory = CvikViewModelFactory(cvikRepository, statisticsRepository)

        // Predvolené cviky
        lifecycleScope.launch {
            val cviky = cvikRepository.allCviky.first()
            if (cviky.isEmpty()) {
                cvikRepository.insert(Cvik(1,"Drepy", "Nohy", 30, R.drawable.drep, R.drawable.drep1, 15))
                cvikRepository.insert(Cvik(2,"Kliky", "Hrudník", 20, R.drawable.klik, R.drawable.klik1, 20))
                cvikRepository.insert(Cvik(3,"Plank", "Core", 60, R.drawable.plank, R.drawable.plank, 50))
            }
        }

        setContent {
            val navController = rememberNavController()
            AppNavGraph(navController = navController, viewModelFactory = viewModelFactory)
        }
    }
}
