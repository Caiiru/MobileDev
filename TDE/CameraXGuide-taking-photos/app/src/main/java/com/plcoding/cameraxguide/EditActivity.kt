package com.plcoding.cameraxguide

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Gradient
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.cameraxguide.ui.theme.EditActivityTheme

class EditActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val imageFilePath = intent.getStringExtra("imageFilePath")
        super.onCreate(savedInstanceState)
        setContent {
            EditActivityTheme {
                // Seu conteúdo da atividade de edição
                //ImageView(bitmap = bitmap)
                EditActivityContent(imageFilePath)


            }
        }
    }
}




@Composable
fun EditActivityContent(imageFilePath: String?){
    var _selectedEffect by remember {
        mutableStateOf<Effect?>(null)
    }
    var brightness by remember { mutableStateOf(1f) }
    var showSlider by remember { mutableStateOf(false) }



    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = "Modo Edição",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(1.dp))

        var _bitmap = BitmapFactory.decodeFile(imageFilePath)
        imageFilePath?.let{
                filePath -> val _bitmap = BitmapFactory.decodeFile(filePath)


            val colorFilter = when (_selectedEffect) {
                Effect.Grayscale -> ColorFilter.colorMatrix(ColorMatrix().apply {
                    setToSaturation(0f)
                })
                Effect.Sepia -> ColorFilter.colorMatrix(ColorMatrix().apply {
                    setToScale(1f, 1f, 0.8f, 1f)
                })
                Effect.Brightness -> ColorFilter.colorMatrix(ColorMatrix().apply {
                    setToScale(brightness,brightness,brightness,1f)
                })
                Effect.Negative -> ColorFilter.colorMatrix(ColorMatrix(floatArrayOf(
                    -1f, 0f, 0f, 0f, 255f, // Red
                    0f, -1f, 0f, 0f, 255f, // Green
                    0f, 0f, -1f, 0f, 255f, // Blue
                    0f, 0f, 0f, 1f, 0f // Alpha
                ))
                )
                else -> null
            }

            Image(bitmap = _bitmap.asImageBitmap(),
                contentDescription = "Bitmap",
                colorFilter = colorFilter,
                modifier = Modifier
                    .size(width = 500.dp, height = 400.dp)
                    .padding(8.dp))

            if(showSlider) {
                Slider(
                    value = brightness, onValueChange = { newBrightness ->
                        brightness = newBrightness
                    }, valueRange = 0f..2f,
                    steps = 100
                )
            }
        }

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){
            EffectButton(
                effect = Effect.Grayscale,
                selectedEffect = _selectedEffect,
                onEffectSelected = {
                    _selectedEffect = it
                    showSlider = false
                }

            )
            EffectButton(
                effect = Effect.Sepia,
                selectedEffect = _selectedEffect,
                onEffectSelected = {
                    _selectedEffect = it
                    showSlider = false
                }
            )
            EffectButton(
                effect = Effect.Brightness,
                selectedEffect = _selectedEffect,
                onEffectSelected = {
                    _selectedEffect = it
                    showSlider = true
                }
            )
            EffectButton(
                effect = Effect.Negative,
                selectedEffect = _selectedEffect,
                onEffectSelected = {
                    _selectedEffect = it
                    showSlider = false
                }
            )


        }

        IconButton( //apply button
            onClick = {
                // Apply the selected effect to the image
                val colorFilter = when (_selectedEffect) {
                    Effect.Grayscale -> ColorFilter.colorMatrix(ColorMatrix().apply {
                        setToSaturation(0f)
                    })
                    Effect.Sepia -> ColorFilter.colorMatrix(ColorMatrix().apply {
                        setToScale(1f, 1f, 0.8f, 1f) // Adjust values for sepia effect
                    })
                    Effect.Negative -> ColorFilter.colorMatrix(ColorMatrix(floatArrayOf(
                        -1f, 0f, 0f, 0f, 255f, // Red
                        0f, -1f, 0f, 0f, 255f, // Green
                        0f, 0f, -1f, 0f, 255f, // Blue
                        0f, 0f, 0f, 1f, 0f // Alpha
                    )))
                    Effect.Brightness -> ColorFilter.colorMatrix(ColorMatrix().apply {
                        setToScale(brightness, brightness, brightness, 1f) // Adjust brightness
                    })
                    else -> null
                }

                val filteredBitmap = _bitmap.copy(Bitmap.Config.ARGB_8888,true)
                _bitmap = filteredBitmap



            }) {
            Icon(imageVector = Icons.Default.Check, contentDescription ="apply filter" )
            Text(text = "apply filter")
        }

    }
}

@Composable
fun EffectButton(
    effect:Effect,
    selectedEffect: Effect?,
    onEffectSelected:(Effect) -> Unit
){
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        IconButton(
            onClick = {onEffectSelected(effect)},
            modifier = Modifier.padding(8.dp)

        ){
            Icon(imageVector = Icons.Default.Gradient, contentDescription = "Effect", Modifier.size(100.dp))

        }
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            modifier = Modifier.paddingFromBaseline(top = 5.dp),
            text = effect.label,
            fontSize=14.sp,
        )
    }



}


enum class Effect(val label:String){
    Grayscale("Grayscale"),
    Sepia("Sepia"),
    Brightness("Brightness"),
    Negative("Negative")
}