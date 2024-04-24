package com.plcoding.cameraxguide

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.plcoding.cameraxguide.ui.theme.CameraXGuideTheme
import com.plcoding.cameraxguide.ui.theme.EditActivityTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

class EditActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val bitmap = intent.getParcelableExtra<Bitmap>("bitmap")
        super.onCreate(savedInstanceState)
        setContent {
            EditActivityTheme {
                // Seu conteúdo da atividade de edição
                //ImageView(bitmap = bitmap)
                EditActivityContent(bitmap = bitmap)
            }
        }



        //val imageView = findViewById<ImageView>(R.id.imageView)
        //imageView.setImageBitmap(bitmap)
    }
}

@Composable
fun Content(){

}
 
@Composable
fun EditActivityContent(bitmap: Bitmap?){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Modo Edição", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        bitmap?.let {
            Image(bitmap = it.asImageBitmap(),
                contentDescription = "Bitmap",
                modifier = Modifier.fillMaxSize())
        }
    }
}
