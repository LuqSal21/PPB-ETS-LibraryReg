package com.example.libraryreg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.libraryreg.ui.theme.AppBackground
import com.example.libraryreg.ui.theme.DarkNavy
import com.example.libraryreg.ui.theme.LibraryRegTheme
import com.example.libraryreg.ui.theme.PrimaryBlue
import com.example.libraryreg.viewmodel.MemberViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPetugasScreen(
    onNavigateBack: () -> Unit,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MemberViewModel? = if (LocalInspectionMode.current) null else viewModel()
) {
    var username by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var isPinVisible by remember { mutableStateOf(false) }

    var usernameError by remember { mutableStateOf<String?>(null) }
    var pinError by remember { mutableStateOf<String?>(null) }
    var loginErrorMessage by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    fun handleLogin() {
        var isValid = true
        usernameError = null
        pinError = null
        loginErrorMessage = null

        if (username.trim().isEmpty()) {
            usernameError = "Username wajib diisi"
            isValid = false
        }

        if (pin.trim().isEmpty()) {
            pinError = "PIN wajib diisi"
            isValid = false
        }

        if (isValid) {
            val success = viewModel?.loginPetugas(username.trim(), pin.trim()) ?: true
            if (success) {
                onLoginSuccess()
            } else {
                loginErrorMessage = "Username atau PIN salah. Silakan coba lagi."
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Login Petugas",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkNavy
                )
            )
        },
        containerColor = AppBackground,
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Badge
            Surface(
                color = Color(0xFFF3E8FF),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF8B5CF6).copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = Color(0xFF6B21A8),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Area Petugas",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF6B21A8)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Masuk Sebagai Petugas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DarkNavy
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Silakan masukkan username dan PIN Anda untuk mengakses halaman pengelolaan anggota.",
                fontSize = 14.sp,
                color = Color(0xFF64748B),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card Form
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (loginErrorMessage != null) {
                        Surface(
                            color = Color(0xFFFEF2F2),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDC2626).copy(alpha = 0.2f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = loginErrorMessage!!,
                                color = Color(0xFFDC2626),
                                fontSize = 13.sp,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    // Username Input
                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            if (usernameError != null) usernameError = null
                            if (loginErrorMessage != null) loginErrorMessage = null
                        },
                        label = { Text("Username") },
                        placeholder = { Text("Contoh: petugas") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryBlue)
                        },
                        isError = usernameError != null,
                        supportingText = {
                            Text(usernameError ?: "Default: petugas")
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // PIN Input
                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                            if (pinError != null) pinError = null
                            if (loginErrorMessage != null) loginErrorMessage = null
                        },
                        label = { Text("PIN") },
                        placeholder = { Text("Contoh: 123456") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = PrimaryBlue)
                        },
                        trailingIcon = {
                            IconButton(onClick = { isPinVisible = !isPinVisible }) {
                                Icon(
                                    imageVector = if (isPinVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (isPinVisible) "Sembunyikan PIN" else "Tampilkan PIN"
                                )
                            }
                        },
                        visualTransformation = if (isPinVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        isError = pinError != null,
                        supportingText = {
                            Text(pinError ?: "Default: 123456")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { handleLogin() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Masuk",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPetugasScreenPreview() {
    LibraryRegTheme {
        LoginPetugasScreen(
            onNavigateBack = {},
            onLoginSuccess = {}
        )
    }
}
