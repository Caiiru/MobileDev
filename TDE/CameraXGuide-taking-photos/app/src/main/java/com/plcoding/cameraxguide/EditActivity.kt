package com.plcoding.cameraxguide

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Gradient
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Undo
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.cameraxguide.ui.theme.EditActivityTheme
import java.io.OutputStream

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

fun saveBitmapToGallery(context: Context, bitmap: Bitmap?,folderName:String,fileName:String){
    val values = ContentValues().apply {
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/$folderName")
        put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.IS_PENDING,1)
    }
    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    uri?.let{
        val outputstream:OutputStream? = resolver.openOutputStream(it)
        outputstream?.use { stream ->
            bitmap?.compress(Bitmap.CompressFormat.PNG,100,stream)

        }
        values.clear()
        values.put(MediaStore.Images.Media.IS_PENDING,0)
        resolver.update(it,values,null,null)
        Toast.makeText(context,"IMAGE SAVED",Toast.LENGTH_SHORT).show()
    }
}


fun loadImage(filePath:String?):Bitmap?{
    return BitmapFactory.decodeFile(filePath)
}
/*
fun applySobelEdgeDetection(bitmap: Bitmap):Bitmap{
    val width = bitmap.width
    val height = bitmap.height
    val sobelMaskX = arrayOf(
        intArrayOf(-1, 0, 1),
        intArrayOf(-2, 0, 2),
        intArrayOf(-1, 0, 1)
    )
    val sobelMaskY = arrayOf(
        intArrayOf(-1, -2, -1),
        intArrayOf(0, 0, 0),
        intArrayOf(1, 2, 1)
    )

    val resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    for (y in 1 until height - 1) {
        for (x in 1 until width - 1) {
            var sumXRed = 0
            var sumYRed = 0
            var sumXGreen = 0
            var sumYGreen = 0
            var sumXBlue = 0
            var sumYBlue = 0

            for (j in -1..1) {
                for (i in -1..1) {
                    val pixel = bitmap.getPixel(x + i, y + j)
                    val weightX = sobelMaskX[j + 1][i + 1]
                    val weightY = sobelMaskY[j + 1][i + 1]

                    sumXRed += Color.red(pixel) * weightX
                    sumYRed += Color.red(pixel) * weightY
                    sumXGreen += Color.green(pixel) * weightX
                    sumYGreen += Color.green(pixel) * weightY
                    sumXBlue += Color.blue(pixel) * weightX
                    sumYBlue += Color.blue(pixel) * weightY
                }
            }

            val magnitudeRed = (sqrt((sumXRed * sumXRed + sumYRed * sumYRed).toDouble()) / 8).toInt()
            val magnitudeGreen = (sqrt((sumXGreen * sumXGreen + sumYGreen * sumYGreen).toDouble()) / 8).toInt()
            val magnitudeBlue = (sqrt((sumXBlue * sumXBlue + sumYBlue * sumYBlue).toDouble()) / 8).toInt()

            val magnitude = Color.rgb(
                minOf(magnitudeRed, 255),
                minOf(magnitudeGreen, 255),
                minOf(magnitudeBlue, 255)
            )

            resultBitmap.setPixel(x, y, magnitude)
        }
    }

    return resultBitmap
}

 */
fun applySobelColorFilter(bitmap: Bitmap): ColorFilter {
    val sobelMatrix = floatArrayOf(
        -1f, 0f, 1f, 0f, 0f,
        -2f, 0f, 2f, 0f, 0f,
        -1f, 0f, 1f, 0f, 0f,
        0f, 0f, 0f, 1f, 0f
    )

    val colorMatrix = ColorMatrix(sobelMatrix)
    return ColorFilter.colorMatrix(colorMatrix)
}
fun createEdgeDetectionColorFilter(bitmap: Bitmap): ColorFilter {
    val sobelX = floatArrayOf(-1f, 0f, 1f, -2f, 0f, 2f, -1f, 0f, 1f)
    val sobelY = floatArrayOf(-1f, -2f, -1f, 0f, 0f, 0f, 1f, 2f, 1f)

    val outputBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
    for (x in 1 until bitmap.width - 1) {
        for (y in 1 until bitmap.height - 1) {
            var sumX = 0f
            var sumY = 0f
            for (i in -1..1) {
                for (j in -1..1) {
                    val pixel = bitmap.getPixel(x + i, y + j)
                    val grayValue = Color.red(pixel) // Assuming grayscale image
                    sumX += grayValue * sobelX[(i + 1) * 3 + j + 1]
                    sumY += grayValue * sobelY[(i + 1) * 3 + j + 1]
                }
            }
            val edgeValue = Math.sqrt((sumX * sumX + sumY * sumY).toDouble()).toInt()
            val newPixel = Color.rgb(edgeValue, edgeValue, edgeValue)
            outputBitmap.setPixel(x, y, newPixel)
        }
    }

    return ColorFilter.colorMatrix(ColorMatrix().apply {
        //setToSaturation(0f) // Convert to grayscale
    })
}


fun applyFilterToBitmap(bitmap: Bitmap?, effect: Effect?, brightness: Float): Bitmap? {
    if (bitmap == null || effect == null) return null

    val width = bitmap.width
    val height = bitmap.height
    val pixels = IntArray(width * height)
    bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

    for (i in pixels.indices) {
        pixels[i] = applyEffect(pixels[i], effect, brightness)
    }

    val outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    outputBitmap.setPixels(pixels, 0, width, 0, 0, width, height)

    return outputBitmap
}

private fun applyEffect(pixel: Int, effect: Effect, brightness: Float): Int {
    val alpha = pixel shr 24 and 0xff
    val red = pixel shr 16 and 0xff
    val green = pixel shr 8 and 0xff
    val blue = pixel and 0xff

    return when (effect) {
        Effect.Grayscale -> {
            val gray = (0.3 * red + 0.59 * green + 0.11 * blue).toInt()
            (alpha shl 24) or (gray shl 16) or (gray shl 8) or gray
        }
        Effect.Sepia -> {
            val newRed = (0.393 * red + 0.769 * green + 0.189 * blue).toInt().coerceAtMost(255)
            val newGreen = (0.349 * red + 0.686 * green + 0.168 * blue).toInt().coerceAtMost(255)
            val newBlue = (0.272 * red + 0.534 * green + 0.131 * blue).toInt().coerceAtMost(255)
            (alpha shl 24) or (newRed shl 16) or (newGreen shl 8) or newBlue
        }
        Effect.Negative -> {
            val newRed = 255 - red
            val newGreen = 255 - green
            val newBlue = 255 - blue
            (alpha shl 24) or (newRed shl 16) or (newGreen shl 8) or newBlue
        }
        Effect.Brightness -> {
            val newRed = (red * brightness).toInt().coerceIn(0, 255)
            val newGreen = (green * brightness).toInt().coerceIn(0, 255)
            val newBlue = (blue * brightness).toInt().coerceIn(0, 255)
            (alpha shl 24) or (newRed shl 16) or (newGreen shl 8) or newBlue
        }
        else -> pixel
    }
}


@Composable
fun EditActivityContent(imageFilePath: String?){
    var _selectedEffect by remember {
        mutableStateOf<Effect?>(null)
    }
    var brightness by remember { mutableStateOf(1f) }
    var showSlider by remember { mutableStateOf(false) }
    var _bitmap by remember { mutableStateOf<Bitmap?>(loadImage(imageFilePath)) }



    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Modo Edição",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(1.dp))

        //var _bitmap = BitmapFactory.decodeFile(imageFilePath)
        imageFilePath?.let{



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
                )))
                Effect.Sobel -> createEdgeDetectionColorFilter(_bitmap!!)
                Effect.None -> ColorFilter.colorMatrix(ColorMatrix().apply { setToScale(1f,1f,1f,1f) })
                Effect.Apply -> ColorFilter.colorMatrix(ColorMatrix().apply { setToScale(1f,1f,1f,1f) })
                else -> null
            }


            Image(bitmap = _bitmap!!.asImageBitmap(),
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
                },
                Icons.Default.Gradient,
                null


            )
            EffectButton(
                effect = Effect.Sepia,
                selectedEffect = _selectedEffect,
                onEffectSelected = {
                    _selectedEffect = it
                    showSlider = false
                },
                Icons.Default.Gradient,
                null
            )
            EffectButton(
                effect = Effect.Brightness,
                selectedEffect = _selectedEffect,
                onEffectSelected = {
                    _selectedEffect = it
                    showSlider = true
                },
                Icons.Default.Gradient,
                null
            )
            EffectButton(
                effect = Effect.Negative,
                selectedEffect = _selectedEffect,
                onEffectSelected = {
                    _selectedEffect = it
                    showSlider = false
                },
                Icons.Default.Gradient,
                null
            )
            EffectButton(
                    effect = Effect.Sobel,
            selectedEffect = _selectedEffect,
            onEffectSelected = {
                _selectedEffect = it
                showSlider = false
            },
            Icons.Default.Gradient,
            null
            )



        }
        Row(
            modifier = Modifier
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center)
             {
            /*
            IconButton( //apply button
                onClick = {
                    _bitmap = applyFilterToBitmap(_bitmap,_selectedEffect,brightness)
                    Log.d("EDIT", "applying EFFECT")
                }) {
                Icon(imageVector = Icons.Default.Check, contentDescription ="apply filter" )
                Spacer(modifier = Modifier.padding(1.dp))
                Text(text = "apply filter")
            }

             */

            EffectButton(
                effect = Effect.Apply,
                selectedEffect = _selectedEffect,
                onEffectSelected = {
                    _bitmap = applyFilterToBitmap(_bitmap,_selectedEffect,brightness)
                    _selectedEffect = it
                    showSlider = false
                },
                Icons.Default.CheckCircle,
                "${_selectedEffect?.label} applied"
            )
            EffectButton(
                effect = Effect.None,
                selectedEffect = _selectedEffect,
                onEffectSelected = {
                    _selectedEffect = it
                    showSlider = false
                },
                Icons.Default.Undo,
                "Undo"
            )
        }
        SaveButton(_bitmap = _bitmap, folderName = "Downloads", fileName ="ImageSaved" )



    }
}
@Composable
fun SaveButton(
    _bitmap: Bitmap?,
    folderName: String,
    fileName: String
){
    val context = LocalContext.current
    Column(modifier = Modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
            IconButton(
                onClick = { saveBitmapToGallery(
                    context = context,
                    bitmap = _bitmap,
                    folderName = folderName,
                    fileName = fileName
                )},
                modifier = Modifier.padding(1.dp)
            ){
                Icon(imageVector = Icons.Default.Save, contentDescription = "SaveButton")
            }
        Spacer(modifier = Modifier.padding(1.dp))
        Text(
            modifier = Modifier.paddingFromBaseline(top = 5.dp),
            text = "Save Image",
            fontSize=14.sp,)
    }
}

@Composable
fun EffectButton(
    effect:Effect,
    selectedEffect: Effect?,
    onEffectSelected:(Effect) -> Unit,
    icon:ImageVector,
    displayMessage:String?
){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        IconButton(
            onClick = {
                onEffectSelected(effect)
                if(displayMessage!=null)
                    Toast.makeText(context,displayMessage,Toast.LENGTH_SHORT).show()
                      },
            modifier = Modifier.padding(8.dp)

        ){
            Icon(imageVector = icon, contentDescription = "Effect", Modifier.size(100.dp))

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
    Negative("Negative"),
    Sobel("Sobel"),
    Apply("Apply"),
    None("Undo")
}
