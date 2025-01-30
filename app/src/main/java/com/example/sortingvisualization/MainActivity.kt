package com.example.sortingvisualization

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sortingvisualization.ui.SortingVisualizer
import com.example.sortingvisualization.ui.theme.SortingVisualizationTheme
import com.example.sortingvisualization.viewmodel.SortingViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    var rotation by remember { mutableFloatStateOf(0f) }
    val animatedRotation by animateFloatAsState(
        targetValue = rotation,
        animationSpec = tween(durationMillis = 2000), label = "" // Adjust speed
    )

    LaunchedEffect(Unit) {
        rotation = 360f // Triggers animation
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.sorting),
            contentDescription = "Splash Image",
            modifier = Modifier
                .fillMaxSize()
                .rotate(animatedRotation)
        )
    }
}

@Composable
fun MainScreen(viewModel: SortingViewModel) {

    if (viewModel.showSplash.value) {
        SplashScreen()
    } else {
        // Determine screen orientation
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        SortingVisualizer(
            viewModel = viewModel,
            isLandscape = isLandscape
        )
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            SortingVisualizationTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: SortingViewModel = viewModel()
                    MainScreen(viewModel)
                }
            }
        }
    }
}