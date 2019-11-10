package com.example.e_vicemote.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.e_vicemote.R;
import com.example.e_vicemote.SettingAccount.EditAlamatToko;
import com.example.e_vicemote.SettingAccount.EditEmail;
import com.example.e_vicemote.SettingAccount.EditJadwalBuka;
import com.example.e_vicemote.SettingAccount.EditLokasiToko;
import com.example.e_vicemote.SettingAccount.EditNamaToko;
import com.example.e_vicemote.SettingAccount.EditPassword;
import com.example.e_vicemote.SettingAccount.EditTelepon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditAccount extends AppCompatActivity {

    String userID, status;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    LinearLayout layout_mitra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        layout_mitra = findViewById(R.id.layout_mitra);

        //user auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("account").child(userID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status = dataSnapshot.child("status").getValue().toString();
                if (status.equals("mitra")){
                    layout_mitra.setVisibility(View.VISIBLE);
                }else {
                    layout_mitra.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(EditAccount.this, MainAccount.class);
        startActivity(intent);
        finish();
    }

    public void edit_telepon(View view) {
        Intent intent = new Intent(EditAccount.this, EditTelepon.class);
        startActivity(intent);
        finish();
    }

    public void edit_email(View view) {
        Intent intent = new Intent(EditAccount.this, EditEmail.class);
        startActivity(intent);
        finish();
    }

    public void edit_password(View view) {
        Intent intent = new Intent(EditAccount.this, EditPassword.class);
        startActivity(intent);
        finish();
    }

    public void edit_nama_toko(View view) {
        Intent intent = new Intent(EditAccount.this, EditNamaToko.class);
        startActivity(intent);
        finish();
    }

    public void edit_alamat_toko(View view) {
        Intent intent = new Intent(EditAccount.this, EditAlamatToko.class);
        startActivity(intent);
        finish();
    }

    public void edit_lokasi_toko(View view) {
        Intent intent = new Intent(EditAccount.this, EditLokasiToko.class);
        startActivity(intent);
        finish();
    }

    public void jadwal_buka(View view) {
        Intent intent = new Intent(EditAccount.this, EditJadwalBuka.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainAccount.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
