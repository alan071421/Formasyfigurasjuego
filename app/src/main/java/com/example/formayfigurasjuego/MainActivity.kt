package com.example.formayfigurasjuego

import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.formayfigurasjuego.R
import com.example.formayfigurasjuego.ui.theme.FormaYFigurasJuegoTheme
import java.util.*

class MainActivity : ComponentActivity() {

    private lateinit var mpCorrect: MediaPlayer
    private lateinit var mpWrong: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar sonidos
        mpCorrect = MediaPlayer.create(this, R.raw.correcto)
        mpWrong = MediaPlayer.create(this, R.raw.incorrecto)

        setContent {
            FormaYFigurasJuegoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShapeGame(
                        playCorrect = { mpCorrect.start() },
                        playWrong = { mpWrong.start() }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mpCorrect.release()
        mpWrong.release()
    }
}

@Composable
fun ShapeGame(
    playCorrect: () -> Unit,
    playWrong: () -> Unit
) {
    var currentShape by remember { mutableStateOf("Círculo") }
    var message by remember { mutableStateOf("¡Encuentra la forma!") }

    // Texto que indica la forma que hay que tocar
    val instruction = "Toca el $currentShape"

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = instruction, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = message, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(32.dp))

        // Fila con las 3 formas
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            ShapeItem(R.drawable.circulo, "Círculo", currentShape, onResult = { correct ->
                if (correct) {
                    playCorrect()
                    message = "¡Muy bien!"
                    currentShape = listOf("Círculo", "Cuadrado", "Triángulo").random()
                } else {
                    playWrong()
                    message = "Intenta otra vez"
                }
            })

            ShapeItem(R.drawable.cuadrado, "Cuadrado", currentShape, onResult = { correct ->
                if (correct) {
                    playCorrect()
                    message = "¡Muy bien!"
                    currentShape = listOf("Círculo", "Cuadrado", "Triángulo").random()
                } else {
                    playWrong()
                    message = "Intenta otra vez"
                }
            })

            ShapeItem(R.drawable.triangulo, "Triángulo", currentShape, onResult = { correct ->
                if (correct) {
                    playCorrect()
                    message = "¡Muy bien!"
                    currentShape = listOf("Círculo", "Cuadrado", "Triángulo").random()
                } else {
                    playWrong()
                    message = "Intenta otra vez"
                }
            })
        }
    }
}

@Composable
fun ShapeItem(
    drawableId: Int,
    shapeName: String,
    currentShape: String,
    onResult: (Boolean) -> Unit
) {
    Image(
        painter = painterResource(id = drawableId),
        contentDescription = shapeName,
        modifier = Modifier
            .size(100.dp)
            .clickable {
                onResult(shapeName == currentShape)
            }
    )
}
