package com.example.androidpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidpedia.ui.theme.AndroidpediaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }
}

@Composable
fun AndroidPediaApp() {
    var currentScreen by remember { mutableStateOf("Welcome") }
    var score by remember { mutableStateOf(0) }
    var currentIndex by remember { mutableStateOf(0) }

    when (currentScreen) {
        "Welcome" -> WelcomeScreen(
            onStart = { currentScreen = "Quiz" }
        )
        "Quiz" -> QuizScreen(
            currentIndex = currentIndex,
            score = score,
            onAnswerCorrect = { score++ },
            onNext = {
                if (currentIndex < quizQuestions.size - 1) {
                    currentIndex++
                } else {
                    currentScreen = "Result"
                }
            }
        )
        "Result" -> ResultScreen(
            score = score,
            onRestart = {
                score = 0
                currentIndex = 0
                currentScreen = "Welcome"
            }
        )
    }
}

@Composable
fun QuizScreen(
    currentIndex: Int,
    score: Int,
    onAnswerCorrect: () -> Unit,
    onNext: () -> Unit
) {
    val question = quizQuestions[currentIndex]
    var selectedOption by remember { mutableStateOf<String?>(null)}
    var hasAnswared by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Pregunta ${currentIndex + 1} de ")
        Text("Puntaje: $score/")

        Card(modifier = Modifier.padding(16.dp)) {
            Text(text = question.question, modifier = Modifier.padding(16.dp))
        }

        question.options.forEach { option ->
            val isCorrect = option == question.correctAnswer
            val isSelected = option == selectedOption

            val buttonColor = when {
                hasAnswared && isCorrect -> Color.Green
                hasAnswared && isSelected && !isCorrect -> Color.Red
                else -> Color.Gray
            }

            Button(
                onClick = {
                    if (!hasAnswared) {
                        selectedOption = option
                        hasAnswered = true
                        if (isCorrect) on AnswerCorrect()
                    }
                },
                enabled = !hasAnswared,
                colors = BurronDefaults.buttonColors(containerColor = buttonColor)
                modifier = Modifier.fillMaxWidth().padding(4.dp)
            ) {
                Text(option)
            }
        }
        if (hasAnswered) {
            Text(text = question.funFact, modifier = Modifier.padding(16.dp))[cite: 106]

            Button(onClick = {
                hasAnswered = false
                selectedOption = null
                onNext()
            }) {
                Text(if (currentIndex < 2) "Siguiente" else "Ver Resultado")[cite: 108, 109]
            }
        }
    }
}