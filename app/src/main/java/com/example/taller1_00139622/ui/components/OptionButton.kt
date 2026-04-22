package com.example.taller1_00139622.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taller1_00139622.R

@Composable
fun OptionButton(
    text: String,
    isCorrect: Boolean,
    isSelected: Boolean,
    hasAnswered: Boolean,
    onClick: () -> Unit
) {
    val targetColor = when {
        hasAnswered && isCorrect -> colorResource(id = R.color.correct_color)
        hasAnswered && isSelected && !isCorrect -> colorResource(id = R.color.incorrect_color)
        else -> colorResource(id = R.color.neutral_color)
    }

    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 300),
        label = "buttonColor"
    )

    Button(
        onClick = onClick,
        enabled = !hasAnswered,
        colors = ButtonDefaults.buttonColors(
            containerColor = animatedColor,
            disabledContainerColor = animatedColor,
            contentColor = colorResource(id = R.color.text_light),
            disabledContentColor = colorResource(id = R.color.text_light)
        ),
        modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}