package com.example.libraryreg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.libraryreg.data.Member
import com.example.libraryreg.ui.theme.AppBackground
import com.example.libraryreg.ui.theme.DarkNavy
import com.example.libraryreg.ui.theme.LibraryRegTheme
import com.example.libraryreg.ui.theme.PrimaryBlue
import com.example.libraryreg.viewmodel.MemberViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAnggotaScreen(
    memberId: Int,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (Int) -> Unit,
    viewModel: MemberViewModel? = if (LocalInspectionMode.current) null else viewModel()
) {
    val isPreview = LocalInspectionMode.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val sampleMember = remember {
        Member(
            id = 1,
            namaLengkap = "Budi Santoso",
            nomorInduk = "210101001",
            programStudi = "Teknik Informatika",
            email = "budi@univ.ac.id",
            nomorTelepon = "081234567890",
            alamat = "Jl. Merdeka No. 10",
            jenisKeanggotaan = "Mahasiswa"
        )
    }

    val memberState = if (isPreview) sampleMember else viewModel?.getMemberById(memberId)?.collectAsState(initial = null)?.value

    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        KonfirmasiHapusAnggotaDialog(
            namaMember = memberState?.namaLengkap ?: "",
            onConfirm = {
                val member = memberState
                if (member != null) {
                    coroutineScope.launch {
                        viewModel?.deleteMember(member)
                        showDeleteDialog = false
                        onNavigateBack()
                    }
                }
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detail Anggota",
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
        containerColor = AppBackground
    ) { innerPadding ->
        if (memberState == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryBlue)
            }
        } else {
            val member = memberState
            val isMahasiswa = member.jenisKeanggotaan.equals("Mahasiswa", ignoreCase = true)
            val badgeBg = if (isMahasiswa) Color(0xFFDBEAFE) else Color(0xFFF3E8FF)
            val badgeText = if (isMahasiswa) Color(0xFF1D4ED8) else Color(0xFF7E22CE)

            val initials = member.namaLengkap.trim()
                .split(" ")
                .mapNotNull { it.firstOrNull() }
                .take(2)
                .joinToString("")
                .uppercase()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Profile Avatar Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFDBEAFE)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (initials.isNotEmpty()) initials else "M",
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                color = PrimaryBlue
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = member.namaLengkap,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkNavy
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Jenis Keanggotaan Badge
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = badgeBg
                            ) {
                                Text(
                                    text = member.jenisKeanggotaan,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = badgeText,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                )
                            }

                            // Status Active Badge
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFDCFCE7)
                            ) {
                                Text(
                                    text = "Aktif",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF15803D),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                // Detail Information Card
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
                        Text(
                            text = "Informasi Keanggotaan",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkNavy
                        )

                        DetailFieldRow(
                            icon = Icons.Default.Badge,
                            label = "NRP",
                            value = member.nomorInduk
                        )
                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        DetailFieldRow(
                            icon = Icons.Default.School,
                            label = "Program Studi",
                            value = member.programStudi
                        )
                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        DetailFieldRow(
                            icon = Icons.Default.Email,
                            label = "Email",
                            value = member.email
                        )
                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        DetailFieldRow(
                            icon = Icons.Default.Phone,
                            label = "Nomor Telepon",
                            value = member.nomorTelepon
                        )
                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        DetailFieldRow(
                            icon = Icons.Default.Home,
                            label = "Alamat",
                            value = member.alamat
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Action Buttons (Edit & Delete)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Edit Button
                    Button(
                        onClick = { onNavigateToEdit(member.id) },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD97706),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Edit Data", fontWeight = FontWeight.Bold)
                    }

                    // Delete Button
                    Button(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFDC2626),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Hapus", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun DetailFieldRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFEFF6FF)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color(0xFF64748B)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkNavy
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailAnggotaScreenPreview() {
    LibraryRegTheme {
        DetailAnggotaScreen(
            memberId = 1,
            onNavigateBack = {},
            onNavigateToEdit = {}
        )
    }
}
