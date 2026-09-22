package com.lifeguard.app.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lifeguard.app.ui.theme.LocalTimePalette

private data class Feature(
    val title: String,
    val subtitle: String,
    val icon: ImageVector
)

private val FEATURES = listOf(
    Feature("Live Safe Map", "AI heatmaps showing risky zones, lighting and crowd density in real time.", Icons.Filled.LocationOn),
    Feature("Shake-to-SOS", "A firm shake instantly alerts your guardians with your GPS coordinates.", Icons.Filled.Warning),
    Feature("Fall Detection", "Sensors detect a fall and auto-escalate if you don't respond.", Icons.Filled.Shield),
    Feature("Family Circle", "See live locations, battery levels and last-seen status of loved ones.", Icons.Filled.Group),
    Feature("Medical Card", "Blood group, allergies and medications on your lock screen — even offline.", Icons.Filled.Favorite),
    Feature("Evidence Vault", "Audio & video auto-recorded and AES-256 encrypted during an SOS.", Icons.Filled.Lock),
    Feature("Safety Timer", "A countdown that auto-triggers SOS if you don't check in.", Icons.Filled.Timer),
    Feature("Offline SMS Backup", "No internet? Emergency SMS with your location goes out instantly.", Icons.Filled.Send)
)

@Composable
fun FeaturesScreen(onContinue: () -> Unit) {
    val palette = LocalTimePalette.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(palette.gradientTop, palette.gradientBottom)))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Everything you need\nto feel safe",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = palette.onSurface
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "LifeGuard bundles emergency response, AI risk awareness and family care into one app.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = palette.onSurface.copy(alpha = 0.7f)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(FEATURES) { feature ->
                    FeatureCard(feature)
                }
                item { Spacer(Modifier.height(8.dp)) }
            }

            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = palette.primary,
                    contentColor = palette.onPrimary
                )
            ) {
                Text(
                    text = "Get Started",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun FeatureCard(feature: Feature) {
    val palette = LocalTimePalette.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(palette.surface.copy(alpha = 0.75f))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(palette.primary.copy(alpha = 0.18f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = feature.icon,
                contentDescription = null,
                tint = palette.primary,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(Modifier.size(14.dp))
        Column {
            Text(
                text = feature.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = palette.onSurface
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = feature.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = palette.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}