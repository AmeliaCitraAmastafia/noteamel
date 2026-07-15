package com.amel.mynote.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amel.mynote.navigation.Screen
import com.amel.mynote.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val NoteColors = listOf(
    0xFFFFF9C4, // Kuning
    0xFFC8E6C9, // Hijau
    0xFFB3E5FC, // Biru
    0xFFF8BBD0, // Pink
    0xFFE1BEE7, // Ungu
    0xFFFFE0B2  // Oranye
)

/**
 * EditorScreen melayani DUA mode dengan satu kode (DRY):
 * - noteId == NO_ID → CREATE : mulai dari teks kosong
 * - noteId != NO_ID → EDIT   : muat isi catatan lama sebagai teks awal
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    viewModel: NoteViewModel,
    noteId: Long,
    onNavigateBack: () -> Unit
) {
    val isEditMode = noteId != Screen.Editor.NO_ID

    // State teks bersifat LOKAL di layar ini (belum perlu masuk ViewModel)
    // karena hanya layar ini yang peduli pada draf yang sedang diketik.
    //
    // rememberSaveable (bukan remember biasa!) → draf SELAMAT dari rotasi
    // layar. Demonstrasi bagus: ganti ke remember{} → ketik → rotasi →
    // draf hilang. Tunjukkan yang RUSAK dulu, baru solusinya. 💡
    val existingNote = if (isEditMode) viewModel.getNoteById(noteId) else null

    var textContent by rememberSaveable {
        mutableStateOf(existingNote?.content.orEmpty())
    }

    var selectedColor by rememberSaveable {
        mutableStateOf(existingNote?.color ?: NoteColors[0])
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (isEditMode) "Edit Catatan" else "Catatan Baru")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.saveNote(
                                id = if (isEditMode) noteId else null,
                                content = textContent,
                                color = selectedColor
                            )
                            onNavigateBack()
                        },
                        enabled = textContent.isNotBlank()
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = "Simpan")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(selectedColor), // Warna TopBar mengikuti pilihan
                    titleContentColor = Color.Black.copy(alpha = 0.7f),
                    navigationIconContentColor = Color.Black.copy(alpha = 0.7f),
                    actionIconContentColor = Color.Black.copy(alpha = 0.7f)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(selectedColor)) // Background seluruh layar mengikuti warna
        ) {
            // Color Picker Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NoteColors.forEach { colorVal ->
                    val color = Color(colorVal)
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (selectedColor == colorVal) 3.dp else 1.dp,
                                color = if (selectedColor == colorVal) Color.DarkGray else Color.Black.copy(alpha = 0.1f),
                                shape = CircleShape
                            )
                            .clickable { selectedColor = colorVal }
                    )
                }
            }
            
            HorizontalDivider(color = Color.Black.copy(alpha = 0.05f))

            if (isEditMode && existingNote != null) {
                Text(
                    text = "Terakhir diubah: ${formatTanggal(existingNote.updatedAt)}",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black.copy(alpha = 0.4f)
                )
            }

            TextField(
                value = textContent,
                onValueChange = { newText -> textContent = newText },
                modifier = Modifier.fillMaxSize(),
                placeholder = { Text("Tulis catatanmu di sini...", color = Color.Black.copy(alpha = 0.4f)) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black.copy(alpha = 0.8f)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

private fun formatTanggal(millis: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
    return formatter.format(Date(millis))
}