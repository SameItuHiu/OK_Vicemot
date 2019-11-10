package com.example.e_vicemote.SettingAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.e_vicemote.Account.EditAccount;
import com.example.e_vicemote.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditAlamatToko extends AppCompatActivity {

    EditText provinsi,kota,alamat;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alamat_toko);

        provinsi = findViewById(R.id.provinsi);
        kota = findViewById(R.id.kota);
        alamat = findViewById(R.id.alamat);

        //user auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("toko").child(userID).child("alamat");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                provinsi.setText(dataSnapshot.child("provinsi").getValue().toString());
                kota.setText(dataSnapshot.child("kota").getValue().toString());
                alamat.setText(dataSnapshot.child("alamat").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void back(View view) {
        Intent intent = new Intent(EditAlamatToko.this, EditAccount.class);
        startActivity(intent);
        finish();
    }

    public void simpan(View view) {
        view = getLayoutInflater().inflate(R.layout.popup_peringatan, null);
        TextView txt_peringatan = view.findViewById(R.id.txt_peringatan);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        if (provinsi.getText().toString().isEmpty()){
            txt_peringatan.setText("Kolom provinsi tidak boleh kosong");
            dialog.show();
        }else if(kota.getText().toString().isEmpty()){
            txt_peringatan.setText("Kolom kota tidak boleh kosong");
            dialog.show();
        }else if(alamat.getText().toString().isEmpty()){
            txt_peringatan.setText("Kolom alamat tidak boleh kosong");
            dialog.show();
        }else{
            txt_peringatan.setText("Alamat toko telah diganti");
            dialog.show();
            databaseReference.child("provinsi").setValue(provinsi.getText().toString());
            databaseReference.child("kota").setValue(kota.getText().toString());
            databaseReference.child("alamat").setValue(alamat.getText().toString());
        }

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, EditAccount.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
