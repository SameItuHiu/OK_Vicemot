package com.example.e_vicemote.OpenService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.e_vicemote.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OpenService2 extends AppCompatActivity {

    public static final String mToko = "";
    public static final String mLayanan = "";
    public static final String mLat = "";
    public static final String mLong = "";

    TextView  Kota, Alamat,txt_peringatan,txt_salam;
    String userID,mContact,nama,lat;

    BottomSheetDialog dialog;

    Spinner Provinsi,Kota1;


    private DatabaseReference ref;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, OpenService.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_service2);
        Kota = findViewById(R.id.kota);
        Alamat = findViewById(R.id.alamat_lengkap);
        txt_salam = findViewById(R.id.salam);

        Provinsi = findViewById(R.id.provinsi);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Provinsi, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Provinsi.setAdapter(adapter);

        Kota1 = findViewById(R.id.kota);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Provinsi, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Kota1.setAdapter(adapter1);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        ref = FirebaseDatabase.getInstance().getReference().child("account").child(userID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mContact = dataSnapshot.child("No_Handphone").getValue().toString();
                nama = dataSnapshot.child("nama").getValue().toString();
                txt_salam.setText("Hello " + nama);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Data", "Error: ", databaseError.toException());
            }
        });
        View view = getLayoutInflater().inflate(R.layout.popup_peringatan, null);
        txt_peringatan = view.findViewById(R.id.txt_peringatan);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
    }

    public void back(View view) {
        startActivity(new Intent(OpenService2.this, OpenService.class));
        finish();
    }

    public void Finish(View view) {

        Bundle bundle = getIntent().getExtras();
        String nama_toko = bundle.getString("nama_toko");
        String layanan = bundle.getString("layanan");

        String mProvinsi = String.valueOf(Provinsi.getSelectedItem());
        String mKota = String.valueOf(Kota1.getSelectedItem());
        String mAlamat = Alamat.getText().toString();

        if (mProvinsi.isEmpty()){
            txt_peringatan.setText("Kolom provinsi tidak boleh kosong");
            dialog.show();
        }else if (mKota.isEmpty()){
            txt_peringatan.setText("Kolom kota tidak boleh kosong");
            dialog.show();
        }else if (mAlamat.isEmpty()){
            txt_peringatan.setText("Kolom alamat tidak boleh kosong");
            dialog.show();
        }else{
            Bundle bundle1 = new Bundle();
            bundle1.putString("nama_toko", nama_toko);
            bundle1.putString("layanan", layanan);
            bundle1.putString("provinsi", mProvinsi);
            bundle1.putString("kota", mKota);
            bundle1.putString("alamat", mAlamat);
            Intent intent = new Intent(OpenService2.this, SetLocation.class);
            intent.putExtras(bundle1);
            startActivity(intent);
            finish();
        }
    }
}
