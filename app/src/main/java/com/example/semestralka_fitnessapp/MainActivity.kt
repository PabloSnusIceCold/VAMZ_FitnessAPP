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
        statisticsRepository = StatisticsRepository(database.statisticsDao())

        // Inicializuj factory pre CvikViewModel
        viewModelFactory = CvikViewModelFactory(cvikRepository, statisticsRepository)

        // Predvolené cviky - vloženie do DB, ak je prázdna
        lifecycleScope.launch {
            val cviky = cvikRepository.allCviky.first()
            if (cviky.isEmpty()) {
                val cvikyNaVlozenie = listOf(
                    Cvik(1, "Drepy", "Nohy", 30, R.drawable.drep, R.drawable.drep1, 15),
                    Cvik(2, "Kliky", "Hrudník", 20, R.drawable.klik, R.drawable.klik1, 20),
                    Cvik(3, "Plank", "Core", 60, R.drawable.plank, R.drawable.plank, 50),
                    Cvik(4, "Angličáky", "Celé telo", 45, R.drawable.burpee, R.drawable.burpee1, 60),
                    Cvik(5, "Výpady", "Nohy", 30, R.drawable.vypad, R.drawable.vypad1, 25),
                    Cvik(6, "Brušáky", "Brucho", 30, R.drawable.situp, R.drawable.situp1, 20),
                    Cvik(7, "Horolezci", "Core", 40, R.drawable.mountain, R.drawable.mountain1, 30),
                    Cvik(8, "Tlaky na ramena", "Ramená", 25, R.drawable.shoulder_press, R.drawable.shoulder_press1, 20),
                    Cvik(9, "Zhyby", "Chrbát", 20, R.drawable.pullup, R.drawable.pullup1, 30),
                    Cvik(10, "Kľuky na tricepsy", "Ruky", 25, R.drawable.triceps_dip, R.drawable.triceps_dip1, 25),
                    Cvik(11, "Skákanie na mieste", "Kardio", 30, R.drawable.jumping, R.drawable.jumping1, 15),
                    Cvik(12, "Bicyklové brušáky", "Brucho", 30, R.drawable.bicycle, R.drawable.bicycle1, 20),
                    Cvik(13, "Sedy-ľahy", "Brucho", 30, R.drawable.crunch, R.drawable.crunch1, 15),
                    Cvik(14, "Drep so skokom", "Nohy", 25, R.drawable.jump_squat, R.drawable.jump_squat1, 35),
                    Cvik(15, "Kladivové zdvihy", "Biceps", 20, R.drawable.hammer_curl, R.drawable.hammer_curl1, 25),
                    Cvik(16, "Ruský twist", "Core", 30, R.drawable.russian_twist, R.drawable.russian_twist1, 25),
                    Cvik(17, "Skákacie výpady", "Nohy", 30, R.drawable.jump_lunge, R.drawable.jump_lunge1, 30),
                    Cvik(18, "Mostík", "Spodný chrbát", 40, R.drawable.bridge, R.drawable.bridge1, 20),
                    Cvik(19, "Stacionárny beh", "Kardio", 60, R.drawable.running, R.drawable.running1, 35),
                    Cvik(20, "Zdvihy kolien v stoji", "Brucho", 30, R.drawable.knee_raise, R.drawable.knee_raise1, 20),
                )
                for (cvik in cvikyNaVlozenie) {
                    cvikRepository.insert(cvik)
                }
            }
        }

        setContent {
            val navController = rememberNavController()
            AppNavGraph(
                navController = navController,
                cvikViewModelFactory = viewModelFactory,
                cvikRepository = cvikRepository,
                statisticsRepository = statisticsRepository
            )
        }
    }
}
