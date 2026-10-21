package com.example.libraryreg.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.libraryreg.R
import com.example.libraryreg.data.Member
import com.example.libraryreg.ui.theme.AppBackground
import com.example.libraryreg.ui.theme.DarkNavy
import com.example.libraryreg.ui.theme.LibraryRegTheme
import com.example.libraryreg.ui.theme.PrimaryBlue
import com.example.libraryreg.viewmodel.MemberViewModel

@Composable
fun PendaftaranBerhasilScreen(
    memberId: Int,
    onDaftarLain: () -> Unit,
    onKembaliBeranda: () -> Unit,
    viewModel: MemberViewModel? = if (LocalInspectionMode.current) null else viewModel()
) {
    val isPreview = LocalInspectionMode.current
    val sampleMember = remember {
        Member(
            id = 1,
            namaLengkap = "Budi Santoso",
            nomorInduk = "210101001",
            programStudi = "Teknik Informatika",
            email = "budi@student.its.ac.id",
            nomorTelepon = "081234567890",
            alamat = "Jl. Merdeka No. 10",
            jenisKeanggotaan = "Mahasiswa"
        )
    }
    val memberState = if (isPreview) sampleMember else viewModel?.getMemberById(memberId)?.collectAsState(initial = null)?.value
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = AppBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_success_circle),
                        contentDescription = "Berhasil",
                        modifier = Modifier.size(72.dp)
                    )

                    Text(
                        text = "Pendaftaran Berhasil!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkNavy,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Anggota baru telah berhasil terdaftar dalam sistem Perpustakaan Universitas.",
                        fontSize = 14.sp,
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center
                    )

                    memberState?.let { member ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFF1F5F9)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                RowDetailItem(label = "Nama Lengkap", value = member.namaLengkap)
                                HorizontalDivider(color = Color(0xFFE2E8F0))
                                RowDetailItem(label = "NRP", value = member.nomorInduk)
                                HorizontalDivider(color = Color(0xFFE2E8F0))
                                RowDetailItem(label = "Program Studi", value = member.programStudi)
                                HorizontalDivider(color = Color(0xFFE2E8F0))
                                RowDetailItem(label = "Jenis Keanggotaan", value = member.jenisKeanggotaan)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onDaftarLain,
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
                            text = "Daftar Anggota Lain",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = onKembaliBeranda,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = PrimaryBlue
                        )
                    ) {
                        Text(
                            text = "Kembali ke Beranda",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RowDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color(0xFF64748B)
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkNavy
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PendaftaranBerhasilScreenPreview() {
    LibraryRegTheme {
        PendaftaranBerhasilScreen(
            memberId = 1,
            onDaftarLain = {},
            onKembaliBeranda = {}
        )
    }
}
