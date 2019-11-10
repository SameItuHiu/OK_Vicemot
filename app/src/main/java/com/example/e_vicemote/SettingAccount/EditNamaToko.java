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

public class EditNamaToko extends AppCompatActivity {

    EditText new_toko ,digit1;
    TextView nama_toko;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String userID, toko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nama_toko);

        new_toko = findViewById(R.id.nama_baru);
        nama_toko = findViewById(R.id.nama_toko);

        //user auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("toko").child(userID).child("nama_toko");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                toko = dataSnapshot.getValue().toString();
                nama_toko.setText(toko);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void back(View view) {
        Intent intent = new Intent(EditNamaToko.this, EditAccount.class);
        startActivity(intent);
        finish();
    }

    public void simpan(View view) {
        view = getLayoutInflater().inflate(R.layout.popup_peringatan, null);
        TextView txt_peringatan = view.findViewById(R.id.txt_peringatan);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        if (new_toko.getText().toString().isEmpty()){
            txt_peringatan.setText("Nama toko tidak boleh kosong");
            dialog.show();
        }else{
            txt_peringatan.setText("Nama toko telah diganti");
            dialog.show();
            databaseReference.setValue(new_toko.getText().toString());
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
