package com.example.appscoba;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appscoba.DataItem;
import com.example.appscoba.DatabaseHelper;
import com.example.appscoba.R;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);

        setupToolbar();
        setupListView();
        loadData();

        // ... (bagian kode lainnya)
    }

    private void setupListView() {
        adapter = new CustomAdapter();
        listView.setAdapter(adapter);

        // Atur tindakan saat item di-klik untuk penghapusan data
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataItem selectedItem = adapter.getItem(position);
                showDeleteConfirmationDialog(selectedItem);
            }
        });
    }

    private class CustomAdapter extends ArrayAdapter<DataItem> {

        public CustomAdapter() {
            super(ViewData.this, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
            }

            final DataItem item = getItem(position);

            TextView tvAgenda = convertView.findViewById(R.id.tvAgenda);
            TextView tvJumlah = convertView.findViewById(R.id.tvJumlah);
            TextView tvHarga = convertView.findViewById(R.id.tvHarga2);
            Button btnDelete = convertView.findViewById(R.id.btnDelete);

            // Tampilkan data di TextViews
            tvAgenda.setText(item.getAgenda());
            tvJumlah.setText(item.getJumlah());
            tvHarga.setText(item.getHarga());

            // Atur tindakan tombol delete
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteConfirmationDialog(item);
                }
            });

            return convertView;
        }
    }

    private void showDeleteConfirmationDialog(final DataItem item){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Tugas");
        builder.setMessage("Apakah anda yakin sudah selesai tugas ini?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteData(item);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deleteData(DataItem item){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int result = db.delete("catatan", "agenda = ?", new String[]{item.getAgenda()});

        if(result > 0){
            Toast.makeText(this, "Tugas telah di hapus", Toast.LENGTH_SHORT).show();
            loadData(); // Reload data to update the ListView
        }
        else {
            Toast.makeText(this, "Gagal menghapus Tugas", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void loadData() {
        ArrayList<DataItem> dataItems = new ArrayList<>();

        // Mengambil data dari database
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM catatan", null);

        // Membaca data satu per satu dari cursor
        while (cursor.moveToNext()) {
            String agenda = cursor.getString(cursor.getColumnIndex("agenda"));
            String jumlah = cursor.getString(cursor.getColumnIndex("waktu"));
            String harga = cursor.getString(cursor.getColumnIndex("keterangan"));

            // Membuat objek DataItem dan menambahkannya ke ArrayList
            DataItem item = new DataItem(agenda, jumlah, harga);
            dataItems.add(item);
            historyItems.add(item);
        }

        // Menutup cursor dan database
        cursor.close();
        db.close();

        // Memperbarui adapter dengan data terbaru
        adapter.clear();
        adapter.addAll(dataItems);
        adapter.notifyDataSetChanged();
    }

    private void setupToolbar() {  //Metode untuk mengatur dan menampilkan toolbar dengan judul "Detail Mobil".
        Toolbar toolbar = findViewById(R.id.tbPenyewa);
        toolbar.setTitle("Detail Tugas");
    }

    private ArrayList<DataItem> historyItems = new ArrayList<>();

    private void showHistory() {
        adapter.clear();
        adapter.addAll(historyItems);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_history_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_history) {
            showHistory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
