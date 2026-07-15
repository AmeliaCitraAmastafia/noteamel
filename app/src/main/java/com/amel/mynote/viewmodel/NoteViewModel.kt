package com.amel.mynote.viewmodel

import androidx.lifecycle.ViewModel
import com.amel.mynote.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * NoteViewModel = "otak" aplikasi yang menyimpan dan mengelola semua catatan.
 *
 * MENGAPA ViewModel?
 * ViewModel bertahan hidup saat configuration change (mis. rotasi layar).
 * Jika daftar catatan disimpan langsung di Composable dengan remember{},
 * data akan HILANG saat layar dirotasi. Coba demonstrasikan ini ke mahasiswa:
 * pindahkan state ke remember{} → rotasi layar → catatan lenyap.
 *
 * MENGAPA StateFlow, bukan variabel biasa?
 * StateFlow adalah "aliran data yang bisa diamati" (observable).
 * Analogi: langganan koran — Dashboard "berlangganan" ke _notes;
 * setiap kali ada edisi baru (data berubah), koran otomatis diantar
 * (UI otomatis recompose). Tanpa Flow, UI tidak tahu data sudah berubah.
 */
class NoteViewModel : ViewModel() {

    // _notes bersifat PRIVATE dan MUTABLE — hanya ViewModel yang boleh mengubah.
    private val _notes = MutableStateFlow<List<Note>>(emptyList())

    // notes bersifat PUBLIC dan READ-ONLY — UI hanya boleh MEMBACA.
    // Pola ini disebut "backing property", pilar dari Unidirectional Data Flow:
    // data mengalir turun (ViewModel → UI), event mengalir naik (UI → ViewModel).
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    // Penghitung ID sederhana. Nanti saat migrasi ke Room,
    // tugas ini diambil alih oleh autoGenerate = true.
    private var nextId = 1L

    /** Mencari catatan berdasarkan id — dipakai EditorScreen saat mode edit. */
    fun getNoteById(id: Long): Note? =
        _notes.value.find { it.id == id }

    /**
     * Satu fungsi untuk DUA skenario (prinsip DRY):
     * - id == null  → CREATE : buat catatan baru
     * - id != null  → UPDATE : perbarui catatan yang sudah ada
     */
    fun saveNote(id: Long?, content: String, color: Long = 0xFFFFF9C4) {
        if (content.isBlank()) return

        _notes.update { currentList ->
            val newList = if (id == null) {
                val now = System.currentTimeMillis()
                listOf(Note(id = nextId++, content = content.trim(), color = color, createdAt = now, updatedAt = now)) + currentList
            } else {
                currentList.map { note ->
                    if (note.id == id) {
                        note.copy(
                            content = content.trim(),
                            color = color,
                            updatedAt = System.currentTimeMillis()
                        )
                    } else {
                        note
                    }
                }
            }
            sortNotes(newList)
        }
    }

    /** Menambah/melepas pin pada catatan */
    fun togglePin(id: Long) {
        _notes.update { currentList ->
            val updatedList = currentList.map { note ->
                if (note.id == id) note.copy(isPinned = !note.isPinned) else note
            }
            sortNotes(updatedList)
        }
    }

    /** Helper untuk mengurutkan catatan: Pin di atas, lalu berdasarkan waktu update terbaru */
    private fun sortNotes(list: List<Note>): List<Note> {
        return list.sortedWith(
            compareByDescending<Note> { it.isPinned }
                .thenByDescending { it.updatedAt }
        )
    }

    /** Bonus: hapus catatan (long-press di Dashboard bisa jadi latihan mandiri). */
    fun deleteNote(id: Long) {
        _notes.update { currentList -> currentList.filterNot { it.id == id } }
    }
}