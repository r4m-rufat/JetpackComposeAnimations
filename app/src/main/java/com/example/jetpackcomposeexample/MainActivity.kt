package com.example.jetpackcomposeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            SimpleColorAnimation()
            Box(contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()) {
                CircularProgressBar(percentage = 0.8f, number = 100)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}

@Composable
fun SimpleColorAnimation(){

    var sizeState by remember {
        mutableStateOf(200.dp)
    }

    val size by animateDpAsState(targetValue = sizeState, tween(durationMillis = 3000, delayMillis = 300, easing = LinearOutSlowInEasing))

    val infiniteTransition = rememberInfiniteTransition()

    val color by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(

            tween(2000),
            repeatMode = RepeatMode.Reverse,

        )
    )

    Box(modifier = Modifier
        .size(size)
        .background(color),
        contentAlignment = Alignment.Center
    ) {

        Button(onClick = { sizeState+=50.dp }) {
            Text(text = "Increase Size")
        }

    }

}

@Composable
fun CircularProgressBar(percentage: Float,
                number: Int,
                fontSize: TextUnit = 28.sp,
                color: Color = Color.Red,
                strokeWidth: Dp = 5.dp,
                animationDuration: Int = 1000,
                animDelay: Int = 200){

    var animationPlay by remember {
        mutableStateOf(false)
    }

    val currentPercentage = animateFloatAsState(targetValue = if (animationPlay) percentage else 0f, animationSpec = tween(durationMillis = animationDuration, delayMillis = animDelay))

    LaunchedEffect(key1 = true){
        animationPlay = true
    }
    
    Box(modifier = Modifier.size(100.dp),
    contentAlignment = Alignment.Center){

        Canvas(modifier = Modifier.size(100.dp)) {
            drawArc(
                color = color,
                -80f,
                360 * currentPercentage.value, 
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        
        Text(text = (currentPercentage.value * number).toInt().toString(),
        color = Color.Black,
        fontSize = fontSize,
        fontWeight =  FontWeight.Bold)

    }

}

