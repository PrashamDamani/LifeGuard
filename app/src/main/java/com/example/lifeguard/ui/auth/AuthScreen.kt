package com.lifeguard.app.ui.auth

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lifeguard.app.ui.theme.LocalTimePalette

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val palette = LocalTimePalette.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) onAuthSuccess()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(palette.gradientTop, palette.gradientBottom)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(palette.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Shield,
                    contentDescription = null,
                    tint = palette.onPrimary,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Welcome to LifeGuard",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = palette.onSurface
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Sign in to continue protecting what matters.",
                style = MaterialTheme.typography.bodyMedium,
                color = palette.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(28.dp))

            // Mode tabs
            val tabs = listOf("Login", "Register", "Phone")
            val selectedIndex = when (state.mode) {
                AuthMode.LOGIN -> 0
                AuthMode.REGISTER -> 1
                AuthMode.PHONE -> 2
            }

            TabRow(
                selectedTabIndex = selectedIndex,
                containerColor = Color.Transparent,
                contentColor = palette.primary,
                indicator = { positions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(positions[selectedIndex]),
                        color = palette.primary
                    )
                },
                divider = {}
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedIndex == index,
                        onClick = {
                            viewModel.onModeChange(
                                when (index) {
                                    0 -> AuthMode.LOGIN
                                    1 -> AuthMode.REGISTER
                                    else -> AuthMode.PHONE
                                }
                            )
                        },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (selectedIndex == index) palette.primary
                                else palette.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            if (state.mode == AuthMode.REGISTER) {
                AuthField(
                    value = state.name,
                    onValueChange = viewModel::onNameChange,
                    label = "Full name",
                    icon = Icons.Filled.Person,
                    keyboardType = KeyboardType.Text
                )
                Spacer(Modifier.height(14.dp))
            }

            if (state.mode == AuthMode.PHONE) {
                AuthField(
                    value = state.phone,
                    onValueChange = viewModel::onPhoneChange,
                    label = "Phone number",
                    icon = Icons.Filled.Phone,
                    keyboardType = KeyboardType.Phone
                )
                Spacer(Modifier.height(14.dp))

                if (state.otpSent) {
                    AuthField(
                        value = state.otp,
                        onValueChange = viewModel::onOtpChange,
                        label = "6-digit OTP",
                        icon = Icons.Filled.Lock,
                        keyboardType = KeyboardType.Number
                    )
                    Spacer(Modifier.height(14.dp))
                }
            } else {
                AuthField(
                    value = state.email,
                    onValueChange = viewModel::onEmailChange,
                    label = "Email",
                    icon = Icons.Filled.Email,
                    keyboardType = KeyboardType.Email
                )
                Spacer(Modifier.height(14.dp))

                AuthField(
                    value = state.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = "Password",
                    icon = Icons.Filled.Lock,
                    keyboardType = KeyboardType.Password,
                    isPassword = true
                )
                Spacer(Modifier.height(14.dp))
            }

            if (state.error != null) {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    if (state.mode == AuthMode.PHONE && !state.otpSent) viewModel.sendOtp()
                    else viewModel.submit()
                },
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = palette.primary,
                    contentColor = palette.onPrimary
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = palette.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = when {
                            state.mode == AuthMode.PHONE && !state.otpSent -> "Send OTP"
                            state.mode == AuthMode.LOGIN -> "Login"
                            state.mode == AuthMode.REGISTER -> "Create Account"
                            else -> "Verify & Continue"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(palette.onSurface.copy(alpha = 0.2f))
                )
                Text(
                    text = "  or continue with  ",
                    style = MaterialTheme.typography.labelSmall,
                    color = palette.onSurface.copy(alpha = 0.6f)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(palette.onSurface.copy(alpha = 0.2f))
                )
            }

            Spacer(Modifier.height(20.dp))

            OutlinedButton(
                onClick = viewModel::signInWithGoogle,
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp, palette.onSurface.copy(alpha = 0.25f)
                )
            ) {
                Text(
                    text = "Continue with Google",
                    color = palette.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "By continuing you agree to LifeGuard's Terms & Privacy Policy.",
                style = MaterialTheme.typography.labelSmall,
                color = palette.onSurface.copy(alpha = 0.55f),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun AuthField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType,
    isPassword: Boolean = false
) {
    val palette = LocalTimePalette.current
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = palette.primary,
            unfocusedBorderColor = palette.onSurface.copy(alpha = 0.25f),
            focusedLabelColor = palette.primary,
            unfocusedLabelColor = palette.onSurface.copy(alpha = 0.65f),
            focusedLeadingIconColor = palette.primary,
            unfocusedLeadingIconColor = palette.onSurface.copy(alpha = 0.6f),
            focusedTextColor = palette.onSurface,
            unfocusedTextColor = palette.onSurface
        )
    )
}
