package com.example.libraryreg.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object PilihPeran : Screen("pilih_peran")
    object FormulirPendaftaran : Screen("formulir_pendaftaran")
    object PendaftaranBerhasil : Screen("pendaftaran_berhasil/{memberId}") {
        fun createRoute(memberId: Int) = "pendaftaran_berhasil/$memberId"
    }
    object LoginPetugas : Screen("login_petugas")
    object DaftarAnggota : Screen("daftar_anggota")
    object DetailAnggota : Screen("detail_anggota/{memberId}") {
        fun createRoute(memberId: Int) = "detail_anggota/$memberId"
    }
    object EditAnggota : Screen("edit_anggota/{memberId}") {
        fun createRoute(memberId: Int) = "edit_anggota/$memberId"
    }
}
