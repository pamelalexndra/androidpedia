package com.example.taller1_00139622.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taller1_00139622.R
import com.example.taller1_00139622.model.Question
import com.example.taller1_00139622.ui.components.OptionButton
import com.example.taller1_00139622.ui.components.QuestionCard
import com.example.taller1_00139622.ui.components.ScoreIndicator

@Composable
fun QuizScreen(
    questions: List<Question>,
    currentIndex: Int,
    score: Int,
    onAnswerCorrect: () -> Unit,
    onNext: () -> Unit
) {
    val question = questions[currentIndex]
    val total = questions.size

    var selectedOption by remember(currentIndex) { mutableStateOf<String?>(null) }
    var hasAnswered by remember(currentIndex) { mutableStateOf(false) }

    val handleOptionSelected: (String) -> Unit = { option ->
        if (!hasAnswered) {
            selectedOption = option
            hasAnswered = true
            if (option == question.correctAnswer) {
                onAnswerCorrect()
            }
        }
    }

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

            ScoreIndicator(currentIndex = currentIndex, total = total, score = score)

            QuestionCard(questionText = question.question)

            question.options.forEach { option ->
                OptionButton(
                    text = option,
                    isCorrect = option == question.correctAnswer,
                    isSelected = option == selectedOption,
                    hasAnswered = hasAnswered,
                    onClick = { handleOptionSelected(option) } // Pasamos solo una función simple
                )
            }

            if (hasAnswered) {
                AnswerFeedback(question = question, isCorrect = selectedOption == question.correctAnswer, onNext = onNext, isLastQuestion = currentIndex == total - 1)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun AnswerFeedback(question: Question, isCorrect: Boolean, onNext: () -> Unit, isLastQuestion: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCorrect) Color(0xFF1B5E20) else Color(0xFF4A0000)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isCorrect) stringResource(R.string.label_correct) else stringResource(R.string.label_incorrect),
                color = if (isCorrect) colorResource(id = R.color.android_green) else Color(0xFFEF9A9A),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = question.funFact,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = colorResource(id = R.color.text_light),
                lineHeight = 20.sp
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = onNext,
        modifier = Modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.android_green))
    ) {
        Text(
            text = if (!isLastQuestion) stringResource(R.string.btn_next) else stringResource(R.string.btn_finish),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.android_dark)
        )
    }
}