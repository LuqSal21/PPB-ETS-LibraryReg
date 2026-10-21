package com.example.libraryreg.ui.screens

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libraryreg.ui.theme.DarkNavy

@Composable
fun KonfirmasiHapusAnggotaDialog(
    namaMember: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White,
        title = {
            Text(
                text = "Hapus Anggota",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = DarkNavy
            )
        },
        text = {
            Text(
                text = "Apakah Anda yakin ingin menghapus data anggota \"$namaMember\"? Tindakan ini tidak dapat dibatalkan.",
                fontSize = 14.sp,
                color = Color(0xFF64748B)
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDC2626),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Hapus", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Batal")
            }
        }
    )
}
