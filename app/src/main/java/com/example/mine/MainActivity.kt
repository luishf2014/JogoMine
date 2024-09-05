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

// State to keep track of clicks

    val clicks = rememberSaveable { mutableStateOf(0) }

    // State to keep track of the target number of clicks

    val targetClicks = rememberSaveable { mutableStateOf((1..50).random()) }

    // State to keep track of the current image

    val currentImage = rememberSaveable { mutableStateOf(R.drawable.primeira) } // Inicial

    // State to control the visibility of the dialogs

    val showCongratulationDialog = rememberSaveable { mutableStateOf(false) }

    val showSurrenderDialog = rememberSaveable { mutableStateOf(false) }

    val showSurrenderImage = rememberSaveable { mutableStateOf(false) } // New state to control surrender image



    // Update the image based on clicks

    val totalClicks = targetClicks.value

    LaunchedEffect(clicks.value) {

        currentImage.value = when {

            clicks.value == totalClicks -> R.drawable.conquista // Imagem de conquista

            clicks.value.toDouble() / totalClicks <= 0.33 -> R.drawable.primeira // Imagem inicial

            clicks.value.toDouble() / totalClicks <= 0.66 -> R.drawable.segunda // Imagem mediana

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

// Display the surrender image and the play again button

            Image(

                painter = painterResource(id = R.drawable.desistir), // Add your surrender image here

                contentDescription = "Surrender",

                modifier = Modifier

                    .fillMaxWidth()

                    .padding(16.dp)

            )

// Button to play again after surrendering

            Button(

                onClick = {

                    // Restart the game

                    clicks.value = 0

                    targetClicks.value = (1..50).random()

                    currentImage.value = R.drawable.primeira

                    showSurrenderImage.value = false // Hide surrender image

                },

                modifier = Modifier.padding(top = 16.dp)

            ) {

                Text("Jogar Novamente")

            }

        } else {

// Display current image as a button

            Image(

                painter = painterResource(id = currentImage.value),

                contentDescription = "Current Stage",

                modifier = Modifier

                    .fillMaxWidth()

                    .padding(16.dp)

                    .clickable {

                        if (clicks.value < totalClicks) {

                            clicks.value += 1

                        }

                    }

            )



// Display clicks count and target

            Text(

                text = "${clicks.value}/${targetClicks.value}",

                fontSize = 24.sp,

                modifier = Modifier.padding(16.dp)

            )



// Button to show surrender dialog

            Button(

                onClick = {

                    showSurrenderDialog.value = true

                }

            ) {

                Text("Desistir")

            }

        }

    }



    // Display the congratulation dialog

    if (showCongratulationDialog.value) {

        CongratulationDialog(

            onPlayAgain = {

                // Restart the game

                clicks.value = 0

                targetClicks.value = (1..50).random()

                currentImage.value = R.drawable.primeira

                showCongratulationDialog.value = false

            },

            onExit = {

                // Handle exit logic

                showCongratulationDialog.value = false

            }

        )

    }



// Display the surrender dialog

    if (showSurrenderDialog.value) {

        SurrenderDialog(

            onPlayAgain = {

                // Show surrender image and hide dialogs

                showSurrenderImage.value = true

                showSurrenderDialog.value = false

            },

            onExit = {

                // Hide the surrender dialog

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

        text = { Text("Você alcançou a conquista!") },

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

        text = { Text("Você deseja iniciar um novo jogo?") },

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