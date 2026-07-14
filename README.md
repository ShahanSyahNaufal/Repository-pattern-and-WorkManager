# Tugas 12: Repository Pattern and WorkManager

## Identitas Lengkap
- **Nama:** muhammad shahan syah naufal abdullah
- **NIM:** 452024611028

## Deskripsi
Aplikasi ini mengimplementasikan Repository Pattern untuk mengabstraksi sumber data dari Room (lokal) dan Retrofit (jaringan). WorkManager digunakan untuk sinkronisasi data secara berkala di latar belakang dengan batasan perangkat harus terhubung ke Wi-Fi dan sedang diisi daya.

## Keuntungan Repository Pattern
Menggunakan Repository Pattern memberikan beberapa keuntungan utama:
1. **Pemisahan Kekhawatiran (Separation of Concerns):** ViewModel tidak perlu tahu detail implementasi dari mana data berasal (API atau Database). Ini membuat kode lebih bersih dan mudah dikelola.
2. **Single Source of Truth:** Repositori memastikan bahwa aplikasi selalu menggunakan data yang konsisten, biasanya dengan mengarahkan ViewModel ke database lokal yang disinkronkan dengan data dari jaringan. Ini memungkinkan aplikasi berjalan luring (offline).

## Bukti Eksekusi
<img alt="12.png" src="/home/yi-sun-sin/RepositorypatternandWorkManager/12.png"/>
<img alt="12.png" src="/home/yi-sun-sin/RepositorypatternandWorkManager/13.png"/>
- Logcat Worker Success:
```
D/RefreshDataWorker: Starting background data refresh
D/RefreshDataWorker: Background refresh successful
```
