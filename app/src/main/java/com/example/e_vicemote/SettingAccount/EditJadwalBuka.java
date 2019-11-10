package com.example.e_vicemote.SettingAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.e_vicemote.Account.EditAccount;
import com.example.e_vicemote.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EditJadwalBuka extends AppCompatActivity {

    private int mHour, mMinute,bJam = 0,bMenit= 0,tJam= 0,tMenit= 0;
    private TextView buka,tutup,txt_peringatan;
    private DatabaseReference ref;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jadwal_buka);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        buka = findViewById(R.id.buka);
        tutup = findViewById(R.id.tutup);

        ref = FirebaseDatabase.getInstance().getReference().child("toko").child(userID);
    }

    public void back(View view) {
        startActivity(new Intent(EditJadwalBuka.this, EditAccount.class));
        finish();
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

    public void UBAH(View view) {
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
            ref.child("jadwal").child("buka").child("jam").setValue(bJam);
            ref.child("jadwal").child("buka").child("menit").setValue(bMenit);
            ref.child("jadwal").child("tutup").child("jam").setValue(tJam);
            ref.child("jadwal").child("tutup").child("menit").setValue(tMenit);
            FirebaseDatabase.getInstance().getReference().child("account").child(userID).child("status").setValue("mitra");
            txt_peringatan.setText("Jadwal sudah diganti");
            dialog.show();
        }
    }
    public void onBackPressed() {
        Intent intent = new Intent(this, EditAccount.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
