package com.example.libraryreg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libraryreg.ui.theme.AppBackground
import com.example.libraryreg.ui.theme.DarkNavy
import com.example.libraryreg.ui.theme.LibraryRegTheme
import com.example.libraryreg.ui.theme.PrimaryBlue

@Composable
fun PilihPeranScreen(
    onSelectCalonAnggota: () -> Unit,
    onSelectPetugas: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = AppBackground,
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))


            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFF2563EB).copy(alpha = 0.1f), RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocalLibrary,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "Perpustakaan Kita",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkNavy
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Selamat datang di Sistem Pendaftaran Anggota Perpustakaan. Silakan pilih peran Anda untuk melanjutkan.",
                fontSize = 14.sp,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            RoleCard(
                title = "Calon Anggota",
                description = "Daftar sebagai anggota baru perpustakaan untuk mendapatkan akses layanan.",
                icon = Icons.Default.PersonAdd,
                iconBackgroundColor = PrimaryBlue,
                arrowBgColor = Color(0xFFDCE9FF),
                arrowColor = Color(0xFF004AC6),
                onClick = onSelectCalonAnggota
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleCard(
                title = "Petugas Perpustakaan",
                description = "Login petugas untuk mengelola data keanggotaan dan pendaftaran.",
                icon = Icons.Default.AdminPanelSettings,
                iconBackgroundColor = Color(0xFF8B5CF6),
                arrowBgColor = Color(0xFFF3E8FF),
                arrowColor = Color(0xFF6B21A8),
                onClick = onSelectPetugas
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Sistem Registrasi Keanggotaan Perpustakaan v1.0",
                fontSize = 12.sp,
                color = Color(0xFF94A3B8),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun RoleCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBackgroundColor: Color,
    arrowBgColor: Color,
    arrowColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(iconBackgroundColor, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkNavy
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color(0xFF64748B),
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(arrowBgColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = arrowColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PilihPeranScreenPreview() {
    LibraryRegTheme {
        PilihPeranScreen(
            onSelectCalonAnggota = {},
            onSelectPetugas = {}
        )
    }
}
