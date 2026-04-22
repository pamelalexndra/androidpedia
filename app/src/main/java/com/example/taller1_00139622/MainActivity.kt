package com.example.taller1_00139622

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.taller1_00139622.data.quizQuestions
import com.example.taller1_00139622.ui.screens.QuizScreen
import com.example.taller1_00139622.ui.screens.ResultScreen
import com.example.taller1_00139622.ui.screens.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                AndroidPediaApp()
            }
        }
    }
}

@Composable
fun AndroidPediaApp() {
    var currentScreen by remember { mutableStateOf("Welcome") }
    var score by rememberSaveable { mutableIntStateOf(0) }
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
    var questions by remember { mutableStateOf(quizQuestions.shuffled().take(3)) }

    when (currentScreen) {
        "Welcome" -> WelcomeScreen(
            onStart = { currentScreen = "Quiz" }
        )
        "Quiz" -> QuizScreen(
            questions = questions,
            currentIndex = currentIndex,
            score = score,
            onAnswerCorrect = { score++ },
            onNext = {
                if (currentIndex < questions.size - 1) {
                    currentIndex++
                } else {
                    currentScreen = "Result"
                }
            }
        )
        "Result" -> ResultScreen(
            score = score,
            total = questions.size,
            onRestart = {
                score = 0
                currentIndex = 0
                questions = quizQuestions.shuffled().take(3)
                currentScreen = "Welcome"
            }
        )
    }
}