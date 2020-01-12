package com.example.e_vicemote.OpenService;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.e_vicemote.MenuUser;
import com.example.e_vicemote.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class OpenService3 extends AppCompatActivity {

    private int mHour, mMinute,bJam = 0,bMenit= 0,tJam= 0,tMenit= 0;
    private TextView buka,tutup,txt_peringatan;
    //private DatabaseReference ref;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_service3);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        buka = findViewById(R.id.buka);
        tutup = findViewById(R.id.tutup);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SetLocation.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public void back(View view) {
        startActivity(new Intent(OpenService3.this, SetLocation.class));
        finish();
    }

    public void Selanjutnya(View view) {
        Bundle bundle = getIntent().getExtras();
        String nama_toko = bundle.getString("nama_toko");
        String layanan = bundle.getString("layanan");
        String provinsi = bundle.getString("provinsi");
        String kota = bundle.getString("kota");
        String alamat = bundle.getString("alamat");
        String Latitut = bundle.getString("Latitut");
        String Longitut = bundle.getString("Longitut");

        view = getLayoutInflater().inflate(R.layout.popup_peringatan, null);
        txt_peringatan = view.findViewById(R.id.txt_peringatan);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        if (bJam == 0 && bMenit == 0){
            txt_peringatan.setText("Anda belum set waktu toko buka");
            dialog.show();
        }else if (tJam == 0 && tMenit == 0){
            txt_peringatan.setText("Anda belum set waktu toko tutup");
            dialog.show();
        }else{

            Bundle bundle1 = new Bundle();
            bundle1.putString("nama_toko", nama_toko);
            bundle1.putString("layanan", layanan);
            bundle1.putString("provinsi", provinsi);
            bundle1.putString("kota", kota);
            bundle1.putString("alamat", alamat);
            bundle1.putString("Latitut", Latitut);
            bundle1.putString("Longitut", Longitut);
            bundle1.putString("bjam", String.valueOf(bJam));
            bundle1.putString("bmenit", String.valueOf(bMenit));
            bundle1.putString("tjam", String.valueOf(tJam));
            bundle1.putString("tmenit", String.valueOf(tMenit));
            Intent intent = new Intent(OpenService3.this, OpenService4.class);
            intent.putExtras(bundle1);
            startActivity(intent);
            finish();
        }

    }

    public void tutup(View view) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        tutup.setText(hourOfDay + " : " +minute+ " WIB ");
                        tJam = hourOfDay;
                        tMenit = minute;


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void buka(View view) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        buka.setText(hourOfDay + " : " +minute+ " WIB ");
                        bJam = hourOfDay;
                        bMenit = minute;

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
