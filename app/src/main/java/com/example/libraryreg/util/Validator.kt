package com.example.libraryreg.util

data class ValidationResult(
    val isValid: Boolean,
    val namaError: String? = null,
    val nomorIndukError: String? = null,
    val programStudiError: String? = null,
    val emailError: String? = null,
    val nomorTeleponError: String? = null,
    val alamatError: String? = null,
    val jenisKeanggotaanError: String? = null
)

object Validator {
    private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun validateNamaLengkap(nama: String): String? {
        if (nama.isBlank()) return "Nama lengkap wajib diisi"
        return null
    }

    fun validateNomorInduk(nomorInduk: String, isDuplicate: Boolean = false): String? {
        if (nomorInduk.isBlank()) return "NRP wajib diisi"
        if (!nomorInduk.all { it.isDigit() }) return "NRP hanya boleh berisi angka"
        if (isDuplicate) return "NRP sudah terdaftar"
        return null
    }

    fun validateProgramStudi(prodi: String): String? {
        if (prodi.isBlank()) return "Pilih program studi"
        return null
    }

    fun validateEmail(email: String): String? {
        if (email.isBlank()) return "Email wajib diisi"
        if (!EMAIL_REGEX.matches(email.trim())) return "Format email tidak valid"
        return null
    }

    fun validateNomorTelepon(phone: String): String? {
        if (phone.isBlank()) return "Nomor telepon wajib diisi"
        if (!phone.all { it.isDigit() }) return "Nomor telepon hanya boleh berisi angka"
        return null
    }

    fun validateAlamat(alamat: String): String? {
        if (alamat.isBlank()) return "Alamat wajib diisi"
        return null
    }

    fun validateJenisKeanggotaan(jenis: String): String? {
        if (jenis.isBlank()) return "Pilih jenis keanggotaan"
        return null
    }

    fun validateMemberInput(
        namaLengkap: String,
        nomorInduk: String,
        programStudi: String,
        email: String,
        nomorTelepon: String,
        alamat: String,
        jenisKeanggotaan: String,
        isNomorIndukDuplicate: Boolean = false
    ): ValidationResult {
        val namaErr = validateNamaLengkap(namaLengkap)
        val nomorIndukErr = validateNomorInduk(nomorInduk, isNomorIndukDuplicate)
        val prodiErr = validateProgramStudi(programStudi)
        val emailErr = validateEmail(email)
        val phoneErr = validateNomorTelepon(nomorTelepon)
        val alamatErr = validateAlamat(alamat)
        val jenisErr = validateJenisKeanggotaan(jenisKeanggotaan)

        val isValid = namaErr == null && nomorIndukErr == null && prodiErr == null &&
                emailErr == null && phoneErr == null && alamatErr == null && jenisErr == null

        return ValidationResult(
            isValid = isValid,
            namaError = namaErr,
            nomorIndukError = nomorIndukErr,
            programStudiError = prodiErr,
            emailError = emailErr,
            nomorTeleponError = phoneErr,
            alamatError = alamatErr,
            jenisKeanggotaanError = jenisErr
        )
    }
}
