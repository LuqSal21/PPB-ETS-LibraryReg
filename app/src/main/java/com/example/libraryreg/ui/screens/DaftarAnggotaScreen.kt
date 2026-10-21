package com.example.libraryreg.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarAnggotaScreen(
    onNavigateToAdd: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToEdit: (Int) -> Unit,
    onLogout: () -> Unit,
    viewModel: MemberViewModel? = if (LocalInspectionMode.current) null else viewModel()
) {
    val isPreview = LocalInspectionMode.current
    val coroutineScope = rememberCoroutineScope()

    var previewQuery by remember { mutableStateOf("") }
    val searchQuery = if (isPreview) previewQuery else viewModel?.searchQuery?.collectAsState()?.value ?: ""

    val sampleMembers = remember {
        listOf(
            Member(id = 1, namaLengkap = "Budi Santoso", nomorInduk = "210101001", programStudi = "Teknik Informatika", email = "budi@univ.ac.id", nomorTelepon = "081234567890", alamat = "Jl. Merdeka No. 10", jenisKeanggotaan = "Mahasiswa"),
            Member(id = 2, namaLengkap = "Dr. Ani Wijaya", nomorInduk = "198503152010122001", programStudi = "Sistem Informasi", email = "ani@univ.ac.id", nomorTelepon = "081987654321", alamat = "Jl. Kampus No. 5", jenisKeanggotaan = "Dosen")
        )
    }
    val membersList = if (isPreview) sampleMembers else viewModel?.members?.collectAsState(initial = emptyList())?.value ?: emptyList()

    var memberToDelete by remember { mutableStateOf<Member?>(null) }

    if (memberToDelete != null) {
        KonfirmasiHapusAnggotaDialog(
            namaMember = memberToDelete?.namaLengkap ?: "",
            onConfirm = {
                val member = memberToDelete
                if (member != null) {
                    coroutineScope.launch {
                        viewModel?.deleteMember(member)
                        memberToDelete = null
                    }
                }
            },
            onDismiss = { memberToDelete = null }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Daftar Anggota",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Kelola data anggota perpustakaan",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel?.logoutPetugas()
                            onLogout()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Keluar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkNavy
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToAdd,
                icon = { Icon(Icons.Default.Add, contentDescription = null, tint = Color.White) },
                text = { Text("Tambah", fontWeight = FontWeight.Bold, color = Color.White) },
                containerColor = PrimaryBlue,
                shape = RoundedCornerShape(24.dp)
            )
        },
        containerColor = AppBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query ->
                    if (isPreview) previewQuery = query else viewModel?.setSearchQuery(query)
                },
                placeholder = { Text("Cari nama atau NRP...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Cari", tint = Color(0xFF64748B))
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            if (isPreview) previewQuery = "" else viewModel?.setSearchQuery("")
                        }) {
                            Icon(Icons.Default.Clear, contentDescription = "Hapus Pencarian")
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = Color(0xFFCBD5E1)
                )
            )

            // Header Total Count
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total: ${membersList.size} Anggota",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkNavy
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Member List or Empty State
            if (membersList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_empty_members),
                            contentDescription = "Tidak ada anggota",
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = if (searchQuery.isBlank()) "Belum Ada Anggota" else "Anggota Tidak Ditemukan",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkNavy
                        )
                        Text(
                            text = if (searchQuery.isBlank())
                                "Silakan tambah anggota baru menggunakan tombol di bawah."
                            else
                                "Tidak ada anggota yang cocok dengan kata kunci \"$searchQuery\".",
                            fontSize = 14.sp,
                            color = Color(0xFF64748B),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(membersList, key = { it.id }) { member ->
                        MemberItemCard(
                            member = member,
                            onClick = { onNavigateToDetail(member.id) },
                            onEdit = { onNavigateToEdit(member.id) },
                            onDelete = { memberToDelete = member }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MemberItemCard(
    member: Member,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val isMahasiswa = member.jenisKeanggotaan.equals("Mahasiswa", ignoreCase = true)
    val badgeBg = if (isMahasiswa) Color(0xFFDBEAFE) else Color(0xFFF3E8FF)
    val badgeText = if (isMahasiswa) Color(0xFF1D4ED8) else Color(0xFF7E22CE)

    val initials = member.namaLengkap.trim()
        .split(" ")
        .mapNotNull { it.firstOrNull() }
        .take(2)
        .joinToString("")
        .uppercase()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Initial Circle Avatar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFDBEAFE)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (initials.isNotEmpty()) initials else "M",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = PrimaryBlue
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Name & Info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = member.namaLengkap,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = DarkNavy,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "NRP: ${member.nomorInduk}",
                        fontSize = 13.sp,
                        color = Color(0xFF64748B)
                    )
                    Text(
                        text = member.programStudi,
                        fontSize = 13.sp,
                        color = Color(0xFF64748B),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = badgeBg,
                    modifier = Modifier.padding(start = 6.dp)
                ) {
                    Text(
                        text = member.jenisKeanggotaan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = badgeText,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(8.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Detail
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Detail",
                        tint = PrimaryBlue
                    )
                }

                // Edit
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color(0xFFD97706)
                    )
                }

                // Delete
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus",
                        tint = Color(0xFFDC2626)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DaftarAnggotaScreenPreview() {
    LibraryRegTheme {
        DaftarAnggotaScreen(
            onNavigateToAdd = {},
            onNavigateToDetail = {},
            onNavigateToEdit = {},
            onLogout = {}
        )
    }
}
