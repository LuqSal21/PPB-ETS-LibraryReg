package com.example.libraryreg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.libraryreg.data.Member
import com.example.libraryreg.ui.theme.AppBackground
import com.example.libraryreg.ui.theme.DarkNavy
import com.example.libraryreg.ui.theme.LibraryRegTheme
import com.example.libraryreg.ui.theme.PrimaryBlue
import com.example.libraryreg.util.Constants
import com.example.libraryreg.util.Validator
import com.example.libraryreg.viewmodel.MemberViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulirPendaftaranScreen(
    onNavigateBack: () -> Unit,
    onSuccess: (Int) -> Unit,
    viewModel: MemberViewModel? = if (LocalInspectionMode.current) null else viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var namaLengkap by remember { mutableStateOf("") }
    var nomorInduk by remember { mutableStateOf("") }
    var programStudi by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nomorTelepon by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var jenisKeanggotaan by remember { mutableStateOf("") }

    var namaError by remember { mutableStateOf<String?>(null) }
    var nomorIndukError by remember { mutableStateOf<String?>(null) }
    var programStudiError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var nomorTeleponError by remember { mutableStateOf<String?>(null) }
    var alamatError by remember { mutableStateOf<String?>(null) }
    var jenisKeanggotaanError by remember { mutableStateOf<String?>(null) }

    var isProgramStudiExpanded by remember { mutableStateOf(false) }
    var isJenisExpanded by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }

    fun validateAndSubmit() {
        coroutineScope.launch {
            val isDuplicate = viewModel?.isNomorIndukExists(nomorInduk.trim()) ?: false
            val validationResult = Validator.validateMemberInput(
                namaLengkap = namaLengkap,
                nomorInduk = nomorInduk,
                programStudi = programStudi,
                email = email,
                nomorTelepon = nomorTelepon,
                alamat = alamat,
                jenisKeanggotaan = jenisKeanggotaan,
                isNomorIndukDuplicate = isDuplicate
            )

            namaError = validationResult.namaError
            nomorIndukError = validationResult.nomorIndukError
            programStudiError = validationResult.programStudiError
            emailError = validationResult.emailError
            nomorTeleponError = validationResult.nomorTeleponError
            alamatError = validationResult.alamatError
            jenisKeanggotaanError = validationResult.jenisKeanggotaanError

            if (validationResult.isValid && !isSubmitting) {
                isSubmitting = true
                val newMember = Member(
                    namaLengkap = namaLengkap.trim(),
                    nomorInduk = nomorInduk.trim(),
                    programStudi = programStudi.trim(),
                    email = email.trim(),
                    nomorTelepon = nomorTelepon.trim(),
                    alamat = alamat.trim(),
                    jenisKeanggotaan = jenisKeanggotaan.trim()
                )
                val insertedId = viewModel?.insertMember(newMember) ?: 1L
                isSubmitting = false
                onSuccess(insertedId.toInt())
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Formulir Pendaftaran",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = DarkNavy
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = DarkNavy
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBackground
                )
            )
        },
        containerColor = AppBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Silakan isi data diri Anda dengan lengkap",
                fontSize = 14.sp,
                color = Color(0xFF64748B),
                modifier = Modifier.padding(bottom = 16.dp)
            )

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
                    // Nama Lengkap
                    OutlinedTextField(
                        value = namaLengkap,
                        onValueChange = {
                            namaLengkap = it
                            if (namaError != null) namaError = null
                        },
                        label = { Text("Nama Lengkap") },
                        placeholder = { Text("Contoh: Budi Santoso") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryBlue)
                        },
                        isError = namaError != null,
                        supportingText = namaError?.let { { Text(it) } },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // NRP
                    OutlinedTextField(
                        value = nomorInduk,
                        onValueChange = {
                            nomorInduk = it
                            if (nomorIndukError != null) nomorIndukError = null
                        },
                        label = { Text("NRP") },
                        placeholder = { Text("Contoh: 210101001") },
                        leadingIcon = {
                            Icon(Icons.Default.Badge, contentDescription = null, tint = PrimaryBlue)
                        },
                        isError = nomorIndukError != null,
                        supportingText = {
                            Text(nomorIndukError ?: "Hanya angka")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Program Studi Dropdown
                    ExposedDropdownMenuBox(
                        expanded = isProgramStudiExpanded,
                        onExpandedChange = { isProgramStudiExpanded = !isProgramStudiExpanded }
                    ) {
                        OutlinedTextField(
                            value = programStudi,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Program Studi") },
                            placeholder = { Text("Pilih Program Studi") },
                            leadingIcon = {
                                Icon(Icons.Default.School, contentDescription = null, tint = PrimaryBlue)
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isProgramStudiExpanded)
                            },
                            isError = programStudiError != null,
                            supportingText = programStudiError?.let { { Text(it) } },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = isProgramStudiExpanded,
                            onDismissRequest = { isProgramStudiExpanded = false }
                        ) {
                            Constants.PROGRAM_STUDI_OPTIONS.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        programStudi = option
                                        programStudiError = null
                                        isProgramStudiExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            if (emailError != null) emailError = null
                        },
                        label = { Text("Email") },
                        placeholder = { Text("Contoh: budi@university.ac.id") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null, tint = PrimaryBlue)
                        },
                        isError = emailError != null,
                        supportingText = emailError?.let { { Text(it) } },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Nomor Telepon
                    OutlinedTextField(
                        value = nomorTelepon,
                        onValueChange = {
                            nomorTelepon = it
                            if (nomorTeleponError != null) nomorTeleponError = null
                        },
                        label = { Text("Nomor Telepon") },
                        placeholder = { Text("Contoh: 081234567890") },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = PrimaryBlue)
                        },
                        isError = nomorTeleponError != null,
                        supportingText = {
                            Text(nomorTeleponError ?: "Hanya angka")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Alamat
                    OutlinedTextField(
                        value = alamat,
                        onValueChange = {
                            alamat = it
                            if (alamatError != null) alamatError = null
                        },
                        label = { Text("Alamat") },
                        placeholder = { Text("Masukkan alamat lengkap Anda") },
                        leadingIcon = {
                            Icon(Icons.Default.Home, contentDescription = null, tint = PrimaryBlue)
                        },
                        isError = alamatError != null,
                        supportingText = alamatError?.let { { Text(it) } },
                        minLines = 3,
                        maxLines = 5,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Jenis Keanggotaan Dropdown
                    ExposedDropdownMenuBox(
                        expanded = isJenisExpanded,
                        onExpandedChange = { isJenisExpanded = !isJenisExpanded }
                    ) {
                        OutlinedTextField(
                            value = jenisKeanggotaan,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Jenis Keanggotaan") },
                            placeholder = { Text("Pilih Jenis Keanggotaan") },
                            leadingIcon = {
                                Icon(Icons.Default.Group, contentDescription = null, tint = PrimaryBlue)
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isJenisExpanded)
                            },
                            isError = jenisKeanggotaanError != null,
                            supportingText = jenisKeanggotaanError?.let { { Text(it) } },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = isJenisExpanded,
                            onDismissRequest = { isJenisExpanded = false }
                        ) {
                            Constants.JENIS_KEANGGOTAAN_OPTIONS.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        jenisKeanggotaan = option
                                        jenisKeanggotaanError = null
                                        isJenisExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Button Daftar
                    Button(
                        onClick = { validateAndSubmit() },
                        enabled = !isSubmitting,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue,
                            contentColor = Color.White
                        )
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Daftar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormulirPendaftaranScreenPreview() {
    LibraryRegTheme {
        FormulirPendaftaranScreen(
            onNavigateBack = {},
            onSuccess = {}
        )
    }
}
