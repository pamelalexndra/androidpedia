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