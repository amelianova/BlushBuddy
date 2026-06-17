# BlushBuddy 💄

Aplikasi loyalitas keanggotaan digital untuk toko skincare. Memungkinkan pelanggan mendaftar sebagai member, mengumpulkan poin dari setiap pembelian, dan menukarkan poin dengan hadiah produk skincare.

---

## Fitur Utama

### 1. Registrasi & Login Member
- Pendaftaran member baru dengan nama lengkap, email, dan nomor HP
- Validasi format email dan kelengkapan data
- Sistem login berbasis email
- Nomor member unik digenerate otomatis dengan format `BB-YYYYMMDD-XXXX`

### 2. Kartu Member Digital
- Kartu keanggotaan virtual ditampilkan di halaman utama
- Menampilkan nama, nomor member, total poin, dan badge tingkat keanggotaan
- **QR Code** unik berdasarkan nomor member untuk keperluan scan di kasir
- Warna kartu berubah dinamis sesuai tingkat keanggotaan

### 3. Sistem Poin & Tingkat Keanggotaan
Poin dihitung otomatis dari setiap transaksi pembelian:

> **Rp 10.000 pembelian = 1 Poin**

Tingkat keanggotaan berdasarkan total poin:

| Tingkat | Poin | Tampilan Kartu |
|---------|------|----------------|
| Bronze Member | 0 – 499 | Gradien Rose Gold |
| Silver Member | 500 – 999 | Gradien Silver |
| Gold Member | 1.000 – 499.999 | Gradien Emas |
| Black Member | 500.000+ | Gradien Hitam Elegan |

### 4. Riwayat Transaksi
- Pencatatan setiap transaksi pembelian beserta keterangan opsional
- Poin yang didapat dari setiap transaksi ditampilkan langsung
- Riwayat diurutkan dari transaksi terbaru
- Preview estimasi poin secara real-time saat input nominal pembelian

### 5. Penukaran Hadiah (Redeem)
Member dapat menukarkan poin dengan produk skincare pilihan:

| Hadiah | Poin |
|--------|------|
| Lip Balm Mini | 50 pt |
| Sheet Mask | 100 pt |
| Travel Moisturizer | 150 pt |
| Serum Sample Vitamin C | 200 pt |
| Mini Skincare Kit | 300 pt |

- Tombol tukar otomatis nonaktif jika poin tidak mencukupi
- Notifikasi berhasil/gagal ditampilkan setelah proses penukaran
- Saldo poin terpotong otomatis setelah redeem berhasil

---

## Alur Navigasi

```
Welcome
├── Daftar → Register → Home
└── Masuk  → Login   → Home
                        ├── Transaksi → Tambah Transaksi
                        ├── Redeem
                        └── Logout → Welcome
```

---

## Tech Stack

| Komponen | Teknologi |
|----------|-----------|
| Bahasa | Kotlin |
| UI Framework | Jetpack Compose (Material 3) |
| Database Lokal | Room (SQLite) |
| Arsitektur | MVVM (ViewModel + StateFlow) |
| Navigasi | Navigation Compose |
| Build System | Gradle Kotlin DSL |
| Min Android | Android 7.0 (API 24) |

---

## Struktur Proyek

```
app/src/main/java/com/example/blushbuddy/
├── data/
│   ├── local/
│   │   ├── dao/          # MemberDao, TransactionDao
│   │   ├── entity/       # MemberEntity, TransactionEntity
│   │   └── BlushBuddyDatabase.kt
│   └── repository/       # BlushBuddyRepository
├── ui/
│   ├── navigation/       # NavGraph, Routes
│   ├── screen/
│   │   ├── welcome/
│   │   ├── register/
│   │   ├── login/
│   │   ├── home/
│   │   ├── transaction/
│   │   └── redeem/
│   └── theme/            # Color, Theme, Typography
└── MainActivity.kt
```
