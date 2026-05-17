package com.example.mydataapp; // Sesuaikan dengan nama package Anda

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    // 1. Deklarasi Variabel
    private EditText etNim, etNama, etProdi, etKelas, etAlamat, etEmail;
    private Button btnTambahData, btnLogout;
    private ListView listViewData;
    private TextView tvWelcome;

    // Variabel untuk menampung data di ListView
    private ArrayList<String> daftarData;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // 2. Hubungkan variabel dengan ID yang ada di XML
        tvWelcome = findViewById(R.id.tvWelcome);
        etNim = findViewById(R.id.etNim);
        etNama = findViewById(R.id.etNama);
        etProdi = findViewById(R.id.etProdi);
        etKelas = findViewById(R.id.etKelas);
        etAlamat = findViewById(R.id.etAlamat);
        etEmail = findViewById(R.id.etEmail);
        btnTambahData = findViewById(R.id.btnTambahData);
        btnLogout = findViewById(R.id.btnLogout);
        listViewData = findViewById(R.id.listViewData);

        // Tampilkan pesan selamat datang dari SharedPreferences/Intent
        SharedPreferences pref = getSharedPreferences("USER_LOG", MODE_PRIVATE);
        String username = pref.getString("username", "Admin");
        tvWelcome.setText("Selamat Datang, " + username);

        // 3. Setup ListView
        daftarData = new ArrayList<>();
        // Gunakan layout sederhana bawaan android: simple_list_item_1
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, daftarData);
        listViewData.setAdapter(adapter);

        // 4. Logika Tombol Tambah Data
        btnTambahData.setOnClickListener(v -> {
            String nim = etNim.getText().toString();
            String nama = etNama.getText().toString();
            String prodi = etProdi.getText().toString();
            String kelas = etKelas.getText().toString();
            String alamat = etAlamat.getText().toString();
            String email = etEmail.getText().toString();

            // Validasi: NIM dan Nama minimal harus diisi
            if (!nim.isEmpty() && !nama.isEmpty()) {
                // Gabungkan semua data menjadi satu teks untuk satu baris list
                String gabungData = "NIM: " + nim + "\nNama: " + nama + "\nProdi: " + prodi +
                        "\nKelas: " + kelas + "\nAlamat: " + alamat + "\nEmail: " + email;

                // Masukkan ke dalam list
                daftarData.add(gabungData);

                // BERITAHU ADAPTER BAHWA ADA DATA BARU (Sangat Penting!)
                adapter.notifyDataSetChanged();

                // Bersihkan form input setelah klik tambah
                bersihkanForm();
                Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "NIM dan Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            }
        });

        // 5. Logika Tombol Logout
        btnLogout.setOnClickListener(v -> {
            // Ubah status isLoggedIn menjadi false
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            // Pindah ke halaman Login (MainActivity)
            Intent intent = new Intent(Dashboard.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void bersihkanForm() {
        etNim.setText("");
        etNama.setText("");
        etProdi.setText("");
        etKelas.setText("");
        etAlamat.setText("");
        etEmail.setText("");
    }
}