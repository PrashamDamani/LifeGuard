package com.lifeguard.app.ui.splash

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifeguard.app.ui.theme.LocalTimePalette
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val SPLASH_FEATURES = listOf(
    "Real-time GPS safety tracking",
    "AI-powered risk heatmaps",
    "Shake-to-SOS emergency trigger",
    "Automatic fall detection",
    "Family Circle live dashboard",
    "Encrypted medical profile",
    "Offline SMS emergency backup",
    "AES-256 evidence vault"
)

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    val palette = LocalTimePalette.current

    val infinite = rememberInfiniteTransition(label = "pulse")
    val pulse by infinite.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    val contentAlpha = remember { Animatable(0f) }
    val contentScale = remember { Animatable(0.85f) }

    var featureIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        launch { contentAlpha.animateTo(1f, tween(700)) }
        launch { contentScale.animateTo(1f, tween(800, easing = FastOutSlowInEasing)) }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1400)
            featureIndex = (featureIndex + 1) % SPLASH_FEATURES.size
        }
    }

    LaunchedEffect(Unit) {
        delay(3400)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(palette.gradientTop, palette.gradientBottom)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .graphicsLayer {
                    alpha = contentAlpha.value
                    scaleX = contentScale.value
                    scaleY = contentScale.value
                }
        ) {
            // Pulsing logo badge
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer {
                        scaleX = pulse
                        scaleY = pulse
                    }
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                palette.primary.copy(alpha = 0.35f),
                                Color.Transparent
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(84.dp)
                        .clip(CircleShape)
                        .background(palette.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Shield,
                        contentDescription = null,
                        tint = palette.onPrimary,
                        modifier = Modifier.size(44.dp)
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            Text(
                text = "LifeGuard",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = palette.onSurface
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Your safety, always on",
                style = MaterialTheme.typography.bodyMedium,
                color = palette.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(48.dp))

            // Auto-scrolling feature ticker
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(palette.surface.copy(alpha = 0.55f)),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = featureIndex,
                    transitionSpec = {
                        (slideInVertically { it } + fadeIn(tween(400))) togetherWith
                                (slideOutVertically { -it } + fadeOut(tween(400)))
                    },
                    label = "featureTicker"
                ) { index ->
                    Text(
                        text = SPLASH_FEATURES[index],
                        style = MaterialTheme.typography.titleMedium,
                        color = palette.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }

            Spacer(Modifier.height(40.dp))

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = palette.primary,
                trackColor = palette.onSurface.copy(alpha = 0.15f)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Preparing your safe space…",
                style = MaterialTheme.typography.labelSmall,
                color = palette.onSurface.copy(alpha = 0.6f),
                fontSize = 12.sp
            )
        }
    }
}