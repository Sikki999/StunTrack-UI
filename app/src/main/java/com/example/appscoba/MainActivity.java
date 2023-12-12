package com.example.appscoba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;

    Animation animTv;
    TextView tvToday, tvMainSalam;

    String hariIni;

    private DatabaseHelper databaseHelper;

    private CardView btnViewData, btnViewJadwal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnViewData = findViewById(R.id.btnViewData);
        btnViewJadwal = findViewById(R.id.btnViewjadwalKuliah);

        databaseHelper = new DatabaseHelper(this);




        floatingActionButton = findViewById(R.id.tambahdiary);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InsertData.class);
                startActivity(intent);
            }
        });


        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewData.class);
                startActivity(intent);
            }

        });

        btnViewJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JadwalKuliah.class);
                startActivity(intent);
            }

        });
        tvToday = findViewById(R.id.tvDate);
        tvMainSalam = findViewById(R.id.tvMainSalam);
        animTv = AnimationUtils.loadAnimation(this, R.anim.anim_tv);
        tvMainSalam.startAnimation(animTv);

        Date dateNow = Calendar.getInstance().getTime();
        hariIni = (String) DateFormat.format("EEEE", dateNow);
        if (hariIni.equalsIgnoreCase("sunday")) {
            hariIni = "minggu";
        } else if (hariIni.equalsIgnoreCase("monday")) {
            hariIni = "Senin";

        } else if (hariIni.equalsIgnoreCase("tuesday")) {
            hariIni = "selasa";

        } else if (hariIni.equalsIgnoreCase("thursday")) {
            hariIni = "kamis";

        } else if (hariIni.equalsIgnoreCase("friday")) {
            hariIni = "jumat";

        } else if (hariIni.equalsIgnoreCase("saturday")) {
            hariIni = "sabtu";

        }
        getToday();
        getSalam();


    }

    private void getSalam() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            tvMainSalam.setText("Semangat Pagi" + " " + "Everyone");
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            tvMainSalam.setText("Semangat Siang" + " " + "Everyone");
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            tvMainSalam.setText("Semangat Sore" + " " + "Everyone");
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            tvMainSalam.setText("Semangat Malam" + " " + "Everyone");

        }


    }

    private void getToday() {
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) DateFormat.format("d", date);
        String monthNumber = (String) DateFormat.format("M", date);
        String year = (String) DateFormat.format("yyyy", date);

        int month = Integer.parseInt(monthNumber);
        String bulan = null;
        if (month == 1) {
            bulan = "Januari";
        } else if (month == 2) {
            bulan = "Februari";
        } else if (month == 3) {
            bulan = "Maret";
        } else if (month == 4) {
            bulan = "April";
        } else if (month == 5) {
            bulan = "Mei";
        } else if (month == 6) {
            bulan = "Juni";
        } else if (month == 7) {
            bulan = "Juli";
        } else if (month == 8) {
            bulan = "Agustus";
        } else if (month == 9) {
            bulan = "September";
        } else if (month == 10) {
            bulan = "Oktober";
        } else if (month == 11) {
            bulan = "November";
        } else if (month == 12) {
            bulan = "Desember";

        }
        String formatFix = hariIni + " , " + tanggal + " " + bulan + " " + year;
        tvToday.setText(formatFix);


    }
}