package com.example.sortingvisualization

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.*
import com.example.sortingvisualization.ui.SortingVisualizer
import com.example.sortingvisualization.ui.theme.SortingVisualizationTheme
import com.example.sortingvisualization.viewmodel.SortingViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onSplashEnd: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sort))
    val animationState = rememberLottieAnimatable()
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(composition) {
        if (composition != null) {
            // Fade in texts slowly
            launch {
                alpha.animateTo(1f, animationSpec = tween(2500))
            }

            // Play the Lottie animation once fully
            animationState.animate(
                composition = composition,
                iterations = 1,
                speed = 3.5f
            )

            // Wait a short moment after animation ends to ensure user sees full animation
            delay(500)

            // Call onSplashEnd to move on
            onSplashEnd()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6A11CB),
                        Color(0xFF2575FC)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            LottieAnimation(
                composition = composition,
                progress = animationState.progress,
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Sorting Visualizer",
                style = TextStyle(
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    shadow = Shadow(
                        color = Color.Cyan.copy(alpha = 0.9f),
                        offset = Offset(4f, 4f),
                        blurRadius = 16f
                    )
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(alpha.value)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Efficient. Fast. Interactive.",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Magenta.copy(alpha = 0.8f),
                    letterSpacing = 1.2.sp,
                    shadow = Shadow(
                        color = Color.Magenta.copy(alpha = 0.7f),
                        offset = Offset(2f, 2f),
                        blurRadius = 8f
                    )
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(alpha.value)
            )
        }
    }
}

@Composable
fun MainScreen(viewModel: SortingViewModel) {
    if (viewModel.showSplash.value) {
        SplashScreen {
            viewModel.showSplash.value = false
        }
    } else {
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
