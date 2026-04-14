package com.example.taller1_00139622

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.example.taller1_00139622.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taller1_00139622.quizQuestions

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
    var score         by remember { mutableIntStateOf(0) }
    var currentIndex  by remember { mutableIntStateOf(0) }

    when (currentScreen) {
        "Welcome" -> WelcomeScreen(
            onStart = { currentScreen = "Quiz" }
        )
        "Quiz" -> QuizScreen(
            currentIndex    = currentIndex,
            score           = score,
            onAnswerCorrect = { score++ },
            onNext          = {
                if (currentIndex < quizQuestions.size - 1) {
                    currentIndex++
                } else {
                    currentScreen = "Result"
                }
            }
        )
        "Result" -> ResultScreen(
            score     = score,
            total     = quizQuestions.size,
            onRestart = {
                score         = 0
                currentIndex  = 0
                currentScreen = "Welcome"
            }
        )
    }
}

@Composable
fun WelcomeScreen(onStart: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.android_dark)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text       = stringResource(id = R.string.app_name),
                fontSize   = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                color      = colorResource(id = R.color.android_green),
                textAlign  = TextAlign.Center
            )

            Text(
                text      = stringResource(id = R.string.welcome_subtitle),
                fontSize  = 18.sp,
                color     = colorResource(id = R.color.text_light),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                shape  = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_bg)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text       = stringResource(id = R.string.student_name),
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color      = colorResource(id = R.color.text_light)
                    )
                    Text(
                        text     = stringResource(id = R.string.student_id),
                        fontSize = 14.sp,
                        color    = colorResource(id = R.color.android_green)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick  = onStart,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape    = RoundedCornerShape(12.dp),
                colors   = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.android_green))
            ) {
                Text(
                    text       = stringResource(id = R.string.btn_start),
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = colorResource(id = R.color.android_dark)
                )
            }
        }
    }
}

@Composable
fun QuizScreen(
    currentIndex    : Int,
    score           : Int,
    onAnswerCorrect : () -> Unit,
    onNext          : () -> Unit
) {
    val question   = quizQuestions[currentIndex]
    val total      = quizQuestions.size

    var selectedOption by remember(currentIndex) { mutableStateOf<String?>(null) }
    var hasAnswered    by remember(currentIndex) { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.android_dark))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text       = stringResource(id = R.string.progress_label, currentIndex + 1, total),
                    fontSize   = 14.sp,
                    color      = colorResource(id = R.color.text_light),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text       = stringResource(id = R.string.score_label, score, total),
                    fontWeight = FontWeight.Bold,
                    fontSize   = 14.sp,
                    color      = colorResource(id = R.color.android_green)
                )
            }

            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / total },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color      = colorResource(id = R.color.android_green),
                trackColor = colorResource(id = R.color.card_bg)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(16.dp),
                colors   = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_bg))
            ) {
                Text(
                    text       = question.question,
                    modifier   = Modifier.padding(20.dp),
                    textAlign  = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    fontSize   = 17.sp,
                    color      = colorResource(id = R.color.text_light),
                    lineHeight = 24.sp
                )
            }

            question.options.forEach { option ->
                val isCorrect  = option == question.correctAnswer
                val isSelected = option == selectedOption

                val targetColor = when {
                    hasAnswered && isCorrect              -> colorResource(id = R.color.correct_color)
                    hasAnswered && isSelected && !isCorrect -> colorResource(id = R.color.incorrect_color)
                    else                                 -> colorResource(id = R.color.neutral_color)
                }

                val animatedColor by animateColorAsState(
                    targetValue = targetColor,
                    animationSpec = tween(durationMillis = 300),
                    label = "buttonColor"
                )

                Button(
                    onClick = {
                        if (!hasAnswered) {
                            selectedOption = option
                            hasAnswered    = true
                            if (isCorrect) onAnswerCorrect()
                        }
                    },
                    enabled  = !hasAnswered,
                    colors   = ButtonDefaults.buttonColors(
                        containerColor        = animatedColor,
                        disabledContainerColor = animatedColor,
                        contentColor          = colorResource(id = R.color.text_light),
                        disabledContentColor  = colorResource(id = R.color.text_light)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text      = option,
                        fontSize  = 15.sp,
                        textAlign = TextAlign.Center,
                        modifier  = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            if (hasAnswered) {
                val isCorrect = selectedOption == question.correctAnswer

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape    = RoundedCornerShape(12.dp),
                    colors   = CardDefaults.cardColors(
                        containerColor = if (isCorrect) Color(0xFF1B5E20) else Color(0xFF4A0000)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text       = if (isCorrect) stringResource(R.string.label_correct) else stringResource(R.string.label_incorrect),
                            color      = if (isCorrect) colorResource(id = R.color.android_green) else Color(0xFFEF9A9A),
                            fontWeight = FontWeight.Bold,
                            fontSize   = 15.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text      = "💡 ${question.funFact}",
                            fontSize  = 14.sp,
                            fontStyle = FontStyle.Italic,
                            color     = colorResource(id = R.color.text_light),
                            lineHeight = 20.sp
                        )
                    }
                }

                Button(
                    onClick  = onNext,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape    = RoundedCornerShape(12.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.android_green))
                ) {
                    Text(
                        text       = if (currentIndex < total - 1) stringResource(R.string.btn_next) else stringResource(R.string.btn_finish),
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color      = colorResource(id = R.color.android_dark)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ResultScreen(
    score     : Int,
    total     : Int,
    onRestart : () -> Unit
) {
    val (emoji, message) = when (score) {
        total       -> "🏆" to "¡Perfecto! Eres todo un experto en Android."
        total - 1   -> "🥈" to "¡Muy bien! Conoces bastante sobre Android."
        1           -> "📚" to "Vas bien, pero todavía hay mucho por aprender."
        else        -> "😅" to "Necesitas estudiar más. Sigue jugando"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.android_dark)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text(text = emoji, fontSize = 72.sp)

            Text(
                text       = stringResource(id = R.string.quiz_finished),
                fontSize   = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color      = colorResource(id = R.color.android_green)
            )

            Card(
                shape    = RoundedCornerShape(16.dp),
                colors   = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_bg)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text       = stringResource(id = R.string.final_score, score, total),
                        fontSize   = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color      = colorResource(id = R.color.android_green)
                    )
                    Text(
                        text       = message,
                        fontSize   = 16.sp,
                        color      = colorResource(id = R.color.text_light),
                        textAlign  = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                }
            }

            Button(
                onClick  = onRestart,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape    = RoundedCornerShape(12.dp),
                colors   = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.android_green))
            ) {
                Text(
                    text       = stringResource(id = R.string.btn_restart),
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = colorResource(id = R.color.android_dark)
                )
            }
        }
    }
}