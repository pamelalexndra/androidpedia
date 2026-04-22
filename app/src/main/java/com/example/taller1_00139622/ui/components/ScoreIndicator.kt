package com.example.taller1_00139622.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taller1_00139622.R

@Composable
fun ScoreIndicator(currentIndex: Int, total: Int, score: Int) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.progress_label, currentIndex + 1, total),
                fontSize = 14.sp,
                color = colorResource(id = R.color.text_light),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = stringResource(id = R.string.score_label, score, total),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = colorResource(id = R.color.android_green)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { (currentIndex + 1).toFloat() / total },
            modifier = Modifier.fillMaxWidth().height(6.dp),
            color = colorResource(id = R.color.android_green),
            trackColor = colorResource(id = R.color.card_bg)
        )
    }
}