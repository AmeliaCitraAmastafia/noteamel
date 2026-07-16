# MyNote 📝

Aplikasi catatan sederhana berbasis Android yang dibangun menggunakan **Jetpack Compose**. Aplikasi ini dirancang untuk memudahkan pengguna mencatat ide, tugas, atau memo harian dengan antarmuka yang modern dan responsif.

## 📸 Tampilan Aplikasi

Berikut adalah beberapa tampilan utama dari aplikasi MyNote. Desain antarmuka menggunakan palet warna pastel yang lembut untuk memberikan kesan nyaman dan fokus saat mencatat:

1. **Dashboard (Kosong)**: Tampilan awal yang minimalis dengan ilustrasi dan teks panduan ("Belum ada catatan"), memudahkan pengguna baru untuk memahami cara memulai aplikasi melalui tombol Floating Action Button (FAB).
<img width="1080" height="2340" alt="Screenshot_20260716_065451" src="https://github.com/user-attachments/assets/873db507-04a5-483b-bc4f-4a09647ef115" />

2. **Editor Catatan**: Ruang menulis yang bersih dengan fitur pemilihan 6 warna tema pastel (Kuning, Hijau, Biru, Pink, Ungu, Oranye). Warna aplikasi akan berubah secara dinamis sesuai pilihan untuk memberikan pengalaman personal.
<img width="1080" height="2340" alt="Screenshot_20260716_065417" src="https://github.com/user-attachments/assets/9591e632-6e47-4a03-bcda-2e748ad9c2b0" />


3. **Dashboard (Berisi)**: Daftar catatan dalam bentuk kartu yang dinamis. Terdapat fitur "Pin" dengan ikon push-pin untuk menandai catatan penting, serta label waktu yang menunjukkan kapan catatan terakhir kali diubah.
<img width="1080" height="2340" alt="Screenshot_20260716_065347" src="https://github.com/user-attachments/assets/e1fb1925-fccd-4239-8a32-e71f863e3bdb" />



## ✨ Fitur Utama
- **Dashboard Catatan**: Menampilkan semua catatan dalam bentuk kartu yang rapi.
- **Tambah & Edit Catatan**: Membuat catatan baru atau memperbarui isi catatan yang sudah ada.
- **Sematkan (Pin) Catatan**: Menandai catatan penting agar selalu berada di posisi paling atas.
- **Pilihan Warna**: Personalisasi catatan dengan berbagai pilihan warna latar belakang yang menarik.
- **Auto-Sort**: Catatan diurutkan secara otomatis berdasarkan status *pin* dan waktu pembaruan terbaru.
- **State Management**: Menggunakan `ViewModel` dan `StateFlow` untuk pengelolaan data yang efisien dan tahan terhadap perubahan orientasi layar.

## 🚀 Teknologi yang Digunakan
- **Kotlin**: Bahasa pemrograman utama.
- **Jetpack Compose**: Toolkit modern untuk membangun UI deklaratif.
- **Navigation Compose**: Navigasi antar layar yang *type-safe*.
- **ViewModel & LiveData/Flow**: Arsitektur komponen untuk manajemen data.
- **Material Design 3**: Standar desain terbaru dari Google.

## 🛠️ Cara Menjalankan
1. *Clone* repository ini.
2. Buka proyek menggunakan **Android Studio (Ladybug atau versi terbaru)**.
3. Tunggu proses Gradle Sync selesai.
4. Jalankan aplikasi di Emulator atau perangkat fisik Android.

---
*Dibuat untuk tugas mata kuliah Pemrograman Mobile.*
