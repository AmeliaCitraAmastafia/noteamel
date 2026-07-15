package com.amel.mynote.model

/**
 * Note adalah "cetakan" (blueprint) satu catatan.
 *
 * MENGAPA data class?
 * - equals()/hashCode() otomatis → Compose bisa mendeteksi perubahan data
 *   secara efisien saat recomposition.
 * - copy() otomatis → mendukung prinsip IMMUTABILITY: kita tidak mengubah
 *   objek lama, melainkan membuat salinan baru dengan nilai berbeda.
 *   Analogi: seperti kertas sticky note — kita tidak menghapus tulisan lama
 *   dengan tip-ex, melainkan menempel kertas baru menggantikannya.
 */
data class Note(
    val id: Long,                 // Identitas unik
    val content: String,          // Isi catatan
    val color: Long = 0xFFFFF9C4, // Warna background
    val isPinned: Boolean = false, // Status Pin
    val createdAt: Long = System.currentTimeMillis(), // Waktu dibuat (tetap)
    val updatedAt: Long = System.currentTimeMillis()  // Waktu diubah (berubah setiap edit)
)