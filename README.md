# worm-game-java.-capstone-project
game ini versi modifikasi dari permainan klasik "snake" yang saya tingkatkan dengan menggunakan elemen visual yang menarik seperti bunga jatuh, snake yang berubah warna ketika memakan makanannya, kepala yang berubah dinamis. pemain akan menghindari tubuhnya sendiri agar tidak game over.
# Project Title: Worms AI-Assisted Development (Game)
## Description
Proyek ini adalah pengembangan dan penyempurnaan game Worms klasik yang dibuat menggunakan Java di Apache NetBeans. Fokus utama dari proyek ini adalah untuk mendemonstrasikan bagaimana kecerdasan buatan, khususnya **IBM Granite**, digunakan secara ekstensif untuk mempercepat, meningkatkan kualitas, dan mendokumentasikan proses pengembangan kode. Game ini menampilkan visual yang menarik seperti bunga jatuh, visual pohon, snake yang berubah warna ketika memakan makanannya, kepala snake yang berubah ubah dengan menggunakan fungsi headshape dengan berubah menjadi bangun datar dengan sesuai kode yang di berikan. AI IBM Granite tidak disertakan dalam produk akhir, melainkan hanya digunakan selama fase pengembangan saja.

## Technologies Used
* **Bahasa Pemrograman:** Java
* **IDE:** Apache NetBeans
* **AI Assistant:** IBM Granite
* **Sistem Operasi:** [Windows 11]
* **Version Control:** Git, GitHub

## Features
Berikut adalah fitur-fitur utama yang ada di game Worms ini, termasuk yang dikembangkan atau diperbaiki dengan bantuan AI:
* **Sistem Gerakan Karakter:** [worm bergerak dengan bebas, dan bisa keluar atau masuk melewati garis]
* **Deteksi Tabrakan:** [worm tidak akan mati ketika menabrak garis, melewati pohon, aatau terkena bunga jatuh. tetapi, worm akan mati ketika memakan dirinya sendiri]
* **Sistem Skor Sederhana:** [skor tidak ada batasan, jadi ketika worm belum memakan dirinya sendiri, game tidak akan selesai]

## Setup Instructions
Untuk menjalankan proyek game Worms ini di komputer Anda:

1.  **Kloning Repositori:**
    Buka Command Prompt/Terminal dan jalankan:
    ```bash
    git clone [https://github.com/nursandang/worm-game-java.-capstone-project.git](https://github.com/nursandang/worm-game-java.-capstone-project.git)
    ```
2.  **Buka di Apache NetBeans:**
    * Buka Apache NetBeans IDE.
    * Klik `File` > `Open Project...`.
    * Navigasikan ke folder `Worm-game-java-capstone-project` yang baru saja Anda kloning, pilih folder proyek NetBeans-nya, dan klik `Open Project`.
3.  **Komplikasi dan Jalankan:**
    * Di NetBeans, klik `Run` > `Run Project` (atau tekan `F6`).
    * Game akan mulai berjalan di jendela terpisah.

## AI Support Explanation (IBM Granite)
Penggunaan IBM Granite AI sangat integral dalam proses pengembangan game ini. Berikut adalah beberapa contoh spesifik bagaimana AI digunakan:

### 1. Peningkatan kualitas kode (Debugging)
* **Skenario:** menambahkan fitur latar belakang yang berganti ganti ketika worm memakan makanannya
* **Interaksi AI:** Bertanya kepada IBM Granite: saya ingin menambahkan fitur latar belakang yang berganti ganti ketika worm memakan makanannya, tolong berikan kode nya atau pemahamannya
* **Saran AI:** IBM Granite memberikan beberapa saran, AI memberikan kodenya, tetapi Ketika saya meng implementasikan saran dari AI IBM granite, ternyata masih ada kendala/tidak sesuai dengan program kode saya. 
* **Dampak:** saya menganilisis lebih lanjut dan menemukan solusinya secara mandiri, yang terinspirasi dari pemahaman saya setelah berinteraksi dengan AI.

### 2. Peningkatan Kualitas Kode & Refactoring
* **Skenario:** Saya ingin memastikan kode untuk keberhasilan mengubah latar belakang menjadi berubah setiap worm memakan makanannya agar lebih efisien dan mudah dibaca.
* **Interaksi AI:** Saya bertanya kepada IBM Granite: (saya memberikan fragmen kode) menggunakan kode program diatas apakah berhasil untuk mengubah latar belakang menjadi berubah setiap worm memakan makanannya?
* **Saran AI:** AI menyarankan untuk mengimplementasikan latar belakang yang berganti-ganti setelah worm membeli makanan, perlu dibuat mechanism dibandingkan dengan kode yang sudah ada: Active List including Background Images: Setelah Anda memiliki implementasikan list kelompok gambar latar belakang seperti yang dijelaskan pada jawaban sebelumnya. Conditional Background Change: Dalam method eatFood() (atau suatu method yang serupa yang menjadikan makanan hilang), sementara makanan hilang, Anda dapat pidan ganti latar belakang secara implicit dengan cek nilai currentBackgroundIndex. Integration with Existing Method: Integrate latar belakang ganti-guru dengan paintComponent() untuk menampilkan gambar latar belakang yang diubah setelah worm membeli makanan, menggunakan metode yang sudah ada.
* **Dampak:** dampaknya saya dapat memodifikasi dari saran AI IBM yang sudah diberikan 

---

**Catatan:** AI IBM Granite adalah alat bantu yang sangat berharga dalam mempercepat proses pengembangan dan mengatasi berbagai tantangan teknis, mulai dari debugging hingga penulisan dokumentasi. Interaksi yang terus-menerus dengan AI membantu dalam pengambilan keputusan teknis dan peningkatan kualitas kode.
