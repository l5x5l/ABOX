package com.strayalpaca.android.abox.ui.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalpaca.android.abox.ui.theme.ABOXTheme

@Composable
fun RoundNumber(modifier: Modifier = Modifier, maxRound: Int = 16, currentRound: Int) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 24.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        var round = maxRound
        while (round >= 2) {
            key(round) {
                RoundNumberText(
                    round = round,
                    isCurrentRound = round == currentRound,
                    isPrevRound = round == (currentRound * 2)
                )
            }
            round /= 2
        }
    }
}

@Composable
fun RoundNumberText(round: Int, isCurrentRound: Boolean = false, isPrevRound: Boolean = false) {
    val selectedTextColor = MaterialTheme.colors.onSurface
    val baseTextColor = Color.Gray
    val textColor = remember {
        Animatable(if (isPrevRound) selectedTextColor else baseTextColor)
    }

    LaunchedEffect(isCurrentRound, isPrevRound) {
        if (isCurrentRound) {
            textColor.animateTo(targetValue = selectedTextColor, animationSpec = tween(1000))
        } else {
            textColor.animateTo(targetValue = baseTextColor, animationSpec = tween(1000))
        }
    }

    Text(text = "$round", style = MaterialTheme.typography.h1, color = textColor.value)
}

@Composable
@Preview("RoundNumber Preview")
fun RoundNumberPreview() {
    val currentRound = remember { mutableStateOf(32) }
    val currentPosition = remember { mutableStateOf(0) }

    ABOXTheme {
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
            RoundNumber(currentRound = currentRound.value)

            Button(onClick = {
                currentRound.value = currentRound.value / 2
                currentPosition.value = 0
            }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Next Round")
            }

            Button(onClick = {
                currentPosition.value += 1
            }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Next Position")
            }

            ListDot(amountOfItem = currentRound.value / 2, currentPosition = currentPosition.value, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}