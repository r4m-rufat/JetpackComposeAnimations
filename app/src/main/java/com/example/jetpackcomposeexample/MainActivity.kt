package com.example.jetpackcomposeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                CircularProgressBar(percentage = 0.8f, number = 100)
//            }

            AnimationVisiblityFunc()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    AnimationVisiblityFunc()

}

@Composable
fun SimpleColorAnimation() {

    var sizeState by remember {
        mutableStateOf(200.dp)
    }

    val size by animateDpAsState(
        targetValue = sizeState,
        tween(durationMillis = 3000, delayMillis = 300, easing = LinearOutSlowInEasing)
    )

    val infiniteTransition = rememberInfiniteTransition()

    val color by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(

            tween(2000),
            repeatMode = RepeatMode.Reverse,

            )
    )

    Box(
        modifier = Modifier
            .size(size)
            .background(color),
        contentAlignment = Alignment.Center
    ) {

        Button(onClick = { sizeState += 50.dp }) {
            Text(text = "Increase Size")
        }

    }

}

@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    color: Color = Color.Red,
    strokeWidth: Dp = 5.dp,
    animationDuration: Int = 1000,
    animDelay: Int = 200
) {

    var animationPlay by remember {
        mutableStateOf(false)
    }

    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlay) percentage else 0f,
        animationSpec = tween(durationMillis = animationDuration, delayMillis = animDelay)
    )

    LaunchedEffect(key1 = true) {
        animationPlay = true
    }

    Box(
        modifier = Modifier.size(100.dp),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.size(100.dp)) {
            drawArc(
                color = color,
                -80f,
                360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        Text(
            text = (currentPercentage.value * number).toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )

    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimationVisiblityFunc() {

    var visiblity by remember {
        mutableStateOf(true)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.android),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(10.dp)
                    .size(50.dp)
                    .clip(
                        CircleShape
                    ),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clickable(onClick = {
                        visiblity = !visiblity
                    }),
                verticalArrangement = Arrangement.Center,

                ) {

                Text(
                    text = "Android",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 18.sp
                )

                Text(
                    text = "Android is great platform",
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    fontSize = 14.sp
                )

            }

            Spacer(modifier = Modifier.height(10.dp))

        }

        AnimatedVisibility(
            visible = visiblity
        ) {

            Image(
                painter = painterResource(id = R.drawable.android),
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(
                        RoundedCornerShape(5)
                    ),
                contentScale = ContentScale.FillBounds
            )

        }

    }

}

