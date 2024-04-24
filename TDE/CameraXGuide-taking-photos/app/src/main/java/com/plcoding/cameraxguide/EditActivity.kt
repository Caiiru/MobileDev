package com.plcoding.cameraxguide

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
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

                val scaffoldState = rememberBottomSheetScaffoldState()
                BottomSheetScaffold(scaffoldState = scaffoldState,
                    sheetPeekHeight = 0.dp,
                    sheetContent = {
                            BottomSheetIconScaffold(modifier = Modifier.padding(16.dp)) {bottomSheetIconType ->
                                when (bottomSheetIconType){
                                    BottomSheetIconType.GRAY ->{
                                        Log.d("DEBUG", "CLICK ON GRAY")
                                    }

                                    BottomSheetIconType.FILTER -> TODO()
                                    BottomSheetIconType.CONTRAST -> TODO()
                                    BottomSheetIconType.EDGE -> TODO()
                                }

                            }


                    }) {

                }

                }
            }
        }



    //val imageView = findViewById<ImageView>(R.id.imageView)
        //imageView.setImageBitmap(bitmap)
    }



 
@Composable
fun EditActivityContent(imageFilePath: String?){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Modo Edição",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        imageFilePath?.let{
            filePath -> val bitmap = BitmapFactory.decodeFile(filePath)

            Image(bitmap = bitmap.asImageBitmap(),
                contentDescription = "Bitmap",
                modifier = Modifier.fillMaxSize())
        }

    }
}

