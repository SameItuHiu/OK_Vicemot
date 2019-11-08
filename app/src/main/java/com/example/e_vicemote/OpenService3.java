package com.example.e_vicemote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class OpenService3 extends AppCompatActivity {

    private int mHour, mMinute,bJam = 0,bMenit= 0,tJam= 0,tMenit= 0;
    private TextView buka,tutup,txt_peringatan;
    private DatabaseReference ref;
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

        ref = FirebaseDatabase.getInstance().getReference().child("toko").child(userID);
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

    public void Finish(View view) {
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
            //ref = FirebaseDatabase.getInstance().getReference().child("toko").child(userID);
            ref.child("nama_toko").setValue(nama_toko);
            ref.child("layanan").setValue(layanan);
            ref.child("alamat").child("provinsi").setValue(provinsi);
            ref.child("alamat").child("kota").setValue(kota);
            ref.child("alamat").child("alamat").setValue(alamat);
            ref.child("alamat").child("kordinat").child("latitude").setValue(Latitut);
            ref.child("alamat").child("kordinat").child("longitude").setValue(Longitut);
            ref.child("jadwal").child("buka").child("jam").setValue(bJam);
            ref.child("jadwal").child("buka").child("menit").setValue(bMenit);
            ref.child("jadwal").child("tutup").child("jam").setValue(tJam);
            ref.child("jadwal").child("tutup").child("menit").setValue(tMenit);
            FirebaseDatabase.getInstance().getReference().child("account").child(userID).child("status").setValue("mitra");
            startActivity(new Intent(OpenService3.this, MenuUser.class));
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
