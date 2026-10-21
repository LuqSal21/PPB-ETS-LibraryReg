package com.example.libraryreg.util

import org.junit.Assert.*
import org.junit.Test

class ValidatorTest {

    @Test
    fun validMemberInput_returnsValidResult() {
        val result = Validator.validateMemberInput(
            namaLengkap = "Budi Santoso",
            nomorInduk = "210101001",
            programStudi = "Teknik Informatika",
            email = "budi@university.ac.id",
            nomorTelepon = "081234567890",
            alamat = "Jl. Merdeka No. 10",
            jenisKeanggotaan = "Mahasiswa",
            isNomorIndukDuplicate = false
        )

        assertTrue(result.isValid)
        assertNull(result.namaError)
        assertNull(result.nomorIndukError)
        assertNull(result.programStudiError)
        assertNull(result.emailError)
        assertNull(result.nomorTeleponError)
        assertNull(result.alamatError)
        assertNull(result.jenisKeanggotaanError)
    }

    @Test
    fun emptyNamaLengkap_returnsError() {
        val result = Validator.validateNamaLengkap("   ")
        assertNotNull(result)
        assertEquals("Nama lengkap wajib diisi", result)
    }

    @Test
    fun emptyNomorInduk_returnsError() {
        val result = Validator.validateNomorInduk("")
        assertNotNull(result)
        assertEquals("NRP wajib diisi", result)
    }

    @Test
    fun nonDigitNomorInduk_returnsError() {
        val result = Validator.validateNomorInduk("210101ABC")
        assertNotNull(result)
        assertEquals("NRP hanya boleh berisi angka", result)
    }

    @Test
    fun duplicateNomorInduk_returnsError() {
        val result = Validator.validateNomorInduk("210101001", isDuplicate = true)
        assertNotNull(result)
        assertEquals("NRP sudah terdaftar", result)
    }

    @Test
    fun emptyProgramStudi_returnsError() {
        val result = Validator.validateProgramStudi("")
        assertNotNull(result)
        assertEquals("Pilih program studi", result)
    }

    @Test
    fun emptyEmail_returnsError() {
        val result = Validator.validateEmail("")
        assertNotNull(result)
        assertEquals("Email wajib diisi", result)
    }

    @Test
    fun invalidFormatEmail_returnsError() {
        val result = Validator.validateEmail("invalid-email-format")
        assertNotNull(result)
        assertEquals("Format email tidak valid", result)
    }

    @Test
    fun emptyNomorTelepon_returnsError() {
        val result = Validator.validateNomorTelepon("")
        assertNotNull(result)
        assertEquals("Nomor telepon wajib diisi", result)
    }

    @Test
    fun nonDigitNomorTelepon_returnsError() {
        val result = Validator.validateNomorTelepon("0812345678a")
        assertNotNull(result)
        assertEquals("Nomor telepon hanya boleh berisi angka", result)
    }

    @Test
    fun emptyAlamat_returnsError() {
        val result = Validator.validateAlamat("  ")
        assertNotNull(result)
        assertEquals("Alamat wajib diisi", result)
    }

    @Test
    fun emptyJenisKeanggotaan_returnsError() {
        val result = Validator.validateJenisKeanggotaan("")
        assertNotNull(result)
        assertEquals("Pilih jenis keanggotaan", result)
    }
}
