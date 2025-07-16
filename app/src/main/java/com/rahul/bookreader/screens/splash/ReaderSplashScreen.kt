package com.rahul.bookreader.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.rahul.bookreader.componets.ReaderLogo
import com.rahul.bookreader.navigation.ReaderScreens
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun ReaderSplashScreen(navController: NavController = rememberNavController()) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(targetValue = 0.9f, animationSpec = tween(durationMillis = 800, easing = {
            OvershootInterpolator(8f).getInterpolation(it)
        }))
        delay(2000L)
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate(ReaderScreens.LoginScreen.name)
        } else {
            navController.navigate(ReaderScreens.HomeScreen.name)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .scale(scale.value), contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .padding(15.dp)
                .size(333.dp),
            shape = CircleShape,
            color = Color.White,
            border = BorderStroke(2.dp, color = Color.LightGray)
        ) {
            Column(modifier = Modifier.padding(1.dp), horizontalAlignment = CenterHorizontally, verticalArrangement = Arrangement.Center) {
                ReaderLogo()
                Text(text = "Read. Change. Yourself", color = Color.Gray, modifier = Modifier.padding(10.dp))
            }
        }
    }

}
