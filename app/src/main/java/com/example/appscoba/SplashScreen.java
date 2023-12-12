package com.example.appscoba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    private int waktu_load = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            // Membuat tugas tertunda (delayed task) yang akan dijalankan setelah waktu tertentu.
            // Dalam kasus ini, kita membuat sebuah objek Handler baru, dan kita memanggil metode "postDelayed" pada objek tersebut.
            // Metode "postDelayed" menerima dua parameter, yaitu sebuah objek "Runnable" dan waktu penundaan dalam milidetik.
            @Override
            public void run() {
                // Setelah waktu penundaan berakhir, kode dalam metode "run" ini akan dijalankan.

                // Membuat objek Intent yang akan berpindah ke MainActivity.
                Intent home = new Intent(SplashScreen.this, MainActivity.class);

                // Memulai aktivitas (Activity) baru dengan menggunakan objek Intent yang sudah dibuat.
                startActivity(home);

                //metode finish() untuk mengakhiri activity SplashScreen agar tidak dapat kembali ke halaman Splash Screen ketika tombol kembali ditekan di MainActivity.
                finish();
            }
        }, waktu_load);


    }
}