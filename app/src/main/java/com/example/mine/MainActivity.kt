package com.example.mine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mine.ui.theme.MineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MineTheme {
                GameScreen()
            }
        }
    }
}

@Composable
fun GameScreen() {
    // Contador de cliques
    val clicks = rememberSaveable { mutableStateOf(0) }

    // Número alvo de cliques para ganhar
    val targetClicks = rememberSaveable { mutableStateOf((1..50).random()) }

    // Imagem atual exibida
    val currentImage = rememberSaveable { mutableStateOf(R.drawable.primeira) }

    // Controle dos diálogos
    val showCongratulationDialog = rememberSaveable { mutableStateOf(false) }
    val showSurrenderDialog = rememberSaveable { mutableStateOf(false) }
    val showSurrenderImage = rememberSaveable { mutableStateOf(false) } // Imagem para desistir

    // Atualiza a imagem conforme o número de cliques
    val totalClicks = targetClicks.value

    LaunchedEffect(clicks.value) {
        currentImage.value = when {
            clicks.value == totalClicks -> R.drawable.conquista // Imagem de vitória
            clicks.value.toDouble() / totalClicks <= 0.33 -> R.drawable.primeira // Imagem inicial
            clicks.value.toDouble() / totalClicks <= 0.66 -> R.drawable.segunda // Imagem intermediária
            else -> R.drawable.terceira // Imagem final
        }

        if (clicks.value == totalClicks) {
            showCongratulationDialog.value = true
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showSurrenderImage.value) {
            // Exibe a imagem de desistência e o botão para reiniciar o jogo
            Image(
                painter = painterResource(id = R.drawable.desistir), // Imagem de desistência
                contentDescription = "Desistir",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // Botão para jogar novamente após desistir
            Button(
                onClick = {
                    // Reinicia o jogo
                    clicks.value = 0
                    targetClicks.value = (1..50).random()
                    currentImage.value = R.drawable.primeira
                    showSurrenderImage.value = false // Oculta a imagem de desistência
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Jogar Novamente")
            }
        } else {
            // Exibe a imagem atual como um botão
            Image(
                painter = painterResource(id = currentImage.value),
                contentDescription = "Estágio Atual",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        if (clicks.value < totalClicks) {
                            clicks.value += 1
                        }
                    }
            )

            // Exibe a contagem de cliques e o alvo ( Apenas para depruração )
            // Text(
            //     text = "${clicks.value}/${targetClicks.value}",
            //     fontSize = 24.sp,
            //     modifier = Modifier.padding(16.dp)
            // )

            // Botão para mostrar o diálogo de desistência
            Button(
                onClick = {
                    showSurrenderDialog.value = true
                }
            ) {
                Text("Desistir")
            }
        }
    }

    // Exibe o diálogo de vitória
    if (showCongratulationDialog.value) {
        CongratulationDialog(
            onPlayAgain = {
                // Reinicia o jogo
                clicks.value = 0
                targetClicks.value = (1..50).random()
                currentImage.value = R.drawable.primeira
                showCongratulationDialog.value = false
            },
            onExit = {
                // Fecha o diálogo de vitória
                showCongratulationDialog.value = false
            }
        )
    }

    // Exibe o diálogo de desistência
    if (showSurrenderDialog.value) {
        SurrenderDialog(
            onPlayAgain = {
                // Mostra a imagem de desistência e fecha o diálogo
                showSurrenderImage.value = true
                showSurrenderDialog.value = false
            },
            onExit = {
                // Fecha o diálogo de desistência
                showSurrenderDialog.value = false
            }
        )
    }
}

@Composable
fun CongratulationDialog(onPlayAgain: () -> Unit, onExit: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Parabéns!") },
        text = { Text("Você conseguiu alcançar o objetivo!") },
        confirmButton = {
            Button(onClick = onPlayAgain) {
                Text("Jogar Novamente")
            }
        },
        dismissButton = {
            Button(onClick = onExit) {
                Text("Sair")
            }
        }
    )
}

@Composable
fun SurrenderDialog(onPlayAgain: () -> Unit, onExit: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Desistência") },
        text = { Text("Quer começar um novo jogo?") },
        confirmButton = {
            Button(onClick = onPlayAgain) {
                Text("Sim")
            }
        },
        dismissButton = {
            Button(onClick = onExit) {
                Text("Não")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MineTheme {
        GameScreen()
    }
}
