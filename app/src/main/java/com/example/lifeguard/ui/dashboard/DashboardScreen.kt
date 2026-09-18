package com.lifeguard.app.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lifeguard.app.ui.theme.LifeGuardGreen
import com.lifeguard.app.ui.theme.LifeGuardRed
import com.lifeguard.app.ui.theme.LocalTimePalette
import java.util.Calendar

private data class DashboardFeature(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val accent: Color
)

@Composable
fun DashboardScreen(onLogout: () -> Unit) {
    val palette = LocalTimePalette.current
    var selectedTab by remember { mutableIntStateOf(0) }

    val features = listOf(
        DashboardFeature("Safe Map", "AI heatmaps", Icons.Filled.LocationOn, palette.primary),
        DashboardFeature("SOS", "Instant alert", Icons.Filled.Warning, LifeGuardRed),
        DashboardFeature("Family Circle", "Live tracking", Icons.Filled.Group, Color(0xFF7E57C2)),
        DashboardFeature("Medical Card", "Lock screen ID", Icons.Filled.Favorite, Color(0xFFEC407A)),
        DashboardFeature("Safety Timer", "Auto check-in", Icons.Filled.Timer, Color(0xFF26A69A)),
        DashboardFeature("Evidence Vault", "Encrypted media", Icons.Filled.Lock, Color(0xFF5C6BC0))
    )

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            NavigationBar(
                containerColor = palette.surface,
                tonalElevation = 0.dp
            ) {
                val items = listOf(
                    "Home" to Icons.Filled.Home,
                    "Map" to Icons.Filled.LocationOn,
                    "Alerts" to Icons.Filled.Notifications,
                    "Profile" to Icons.Filled.Person
                )
                items.forEachIndexed { index, (label, icon) ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = palette.primary,
                            selectedTextColor = palette.primary,
                            indicatorColor = palette.primary.copy(alpha = 0.15f),
                            unselectedIconColor = palette.onSurface.copy(alpha = 0.6f),
                            unselectedTextColor = palette.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(palette.gradientTop, palette.gradientBottom)))
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(20.dp))

                Header(greeting = greeting(), paletteLabel = palette.label)

                Spacer(Modifier.height(20.dp))

                SafetyStatusCard()

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = palette.onSurface
                )

                Spacer(Modifier.height(12.dp))

                // 2-column grid rendered without LazyVerticalGrid (avoids nested scroll issues)
                features.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEach { feature ->
                            FeatureTile(
                                feature = feature,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(12.dp))
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

private fun greeting(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        in 17..20 -> "Good evening"
        else -> "Good night"
    }
}

@Composable
private fun Header(greeting: String, paletteLabel: String) {
    val palette = LocalTimePalette.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = greeting,
                style = MaterialTheme.typography.bodyMedium,
                color = palette.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = "Aarav Sharma",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = palette.onSurface
            )
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(palette.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "AS",
                color = palette.onPrimary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
    Spacer(Modifier.height(6.dp))
    Text(
        text = "$paletteLabel mode · sensors active",
        style = MaterialTheme.typography.labelSmall,
        color = palette.onSurface.copy(alpha = 0.55f)
    )
}

@Composable
private fun SafetyStatusCard() {
    val palette = LocalTimePalette.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(
                        palette.primary.copy(alpha = 0.22f),
                        palette.primary.copy(alpha = 0.08f)
                    )
                )
            )
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(LifeGuardGreen.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Shield,
                    contentDescription = null,
                    tint = LifeGuardGreen
                )
            }
            Spacer(Modifier.size(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "You're protected",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = palette.onSurface
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "Fall detection · Location · Family Circle",
                    style = MaterialTheme.typography.bodyMedium,
                    color = palette.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun FeatureTile(
    feature: DashboardFeature,
    modifier: Modifier = Modifier
) {
    val palette = LocalTimePalette.current
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(palette.surface.copy(alpha = 0.78f))
            .clickable { /* navigate later */ }
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(feature.accent.copy(alpha = 0.18f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = feature.icon,
                contentDescription = null,
                tint = feature.accent,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(Modifier.height(14.dp))
        Text(
            text = feature.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = palette.onSurface
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = feature.subtitle,
            style = MaterialTheme.typography.labelSmall,
            color = palette.onSurface.copy(alpha = 0.65f)
        )
    }
}
