package com.example.taller1_00139622.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taller1_00139622.R

@Composable
fun ResultScreen(
    score     : Int,
    total     : Int,
    onRestart : () -> Unit
) {
    val (emoji, message) = when (score) {
        total       -> "🏆" to "¡Perfecto! Eres todo un experto"
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