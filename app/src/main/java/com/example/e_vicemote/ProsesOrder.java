package com.example.e_vicemote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProsesOrder extends AppCompatActivity {

    public static final String key = null;

    String userID, nama_toko, layanan, keyID,id,nama_pelanggan;
    private DatabaseReference ref;

    private int mYear, mMonth, mDay, mHour, mMinute,nYear,nMonth,nDay;

    TextView info_barang, keluhan, mToko, mLayanan, txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses_order);

        //init textview
        info_barang = findViewById(R.id.info_barang);
        keluhan = findViewById(R.id.keluhan);
        mToko = findViewById(R.id.nToko);
        mLayanan = findViewById(R.id.nDesk);

        keyID = getIntent().getStringExtra(key);

        //user auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        //init data from firebase
        ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                id = ref.child("account").child("order").push().getKey();
                nama_toko = dataSnapshot.child("toko").child(keyID).child("nama_toko").getValue().toString();
                layanan = dataSnapshot.child("toko").child(keyID).child("layanan").getValue().toString();
                nama_pelanggan = dataSnapshot.child("account").child(userID).child("nama").getValue().toString();
                mToko.setText(nama_toko);
                mLayanan.setText(layanan);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // get the bottom sheet view
        View view = getLayoutInflater().inflate(R.layout.popup_order, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        Button kirim = findViewById(R.id.btn_kirim);
        Button cek_order = view.findViewById(R.id.btn_cek_order);
        ImageView close = view.findViewById(R.id.close);

        cek_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProsesOrder.this, RoomChat.class));
                finish();
                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(ProsesOrder.this, MenuUser.class));
                finish();
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nInfo_barang = info_barang.getText().toString();
                String nKeluhan = keluhan.getText().toString();

                ref.child("account").child(userID).child("order").child(id).child("id_order").setValue(id);
                ref.child("account").child(userID).child("order").child(id).child("id_mitra").setValue(keyID);
                ref.child("account").child(userID).child("order").child(id).child("nama_toko").setValue(nama_toko);
                ref.child("account").child(userID).child("order").child(id).child("info_barang").setValue(nInfo_barang);
                ref.child("account").child(userID).child("order").child(id).child("keluhan").setValue(nKeluhan);
                ref.child("account").child(userID).child("order").child(id).child("status_order").setValue("Order Terkirim");
                ref.child("account").child(userID).child("order").child(id).child("order_dibuat").child("day").setValue(mDay);
                ref.child("account").child(userID).child("order").child(id).child("order_dibuat").child("month").setValue(mMonth);
                ref.child("account").child(userID).child("order").child(id).child("order_dibuat").child("year").setValue(mYear);
                ref.child("account").child(userID).child("order").child(id).child("order_dibuat").child("hour").setValue(mHour);
                ref.child("account").child(userID).child("order").child(id).child("order_dibuat").child("minute").setValue(mMinute);
                ref.child("account").child(userID).child("order").child(id).child("arrival").child("day").setValue(nDay);
                ref.child("account").child(userID).child("order").child(id).child("arrival").child("month").setValue(nMonth);
                ref.child("account").child(userID).child("order").child(id).child("arrival").child("year").setValue(nYear);
                ref.child("account").child(userID).child("alert").setValue(true);

                ref.child("toko").child(keyID).child("order").child(id).child("status_order").setValue("Order Masuk");
                ref.child("toko").child(keyID).child("order").child(id).child("id_order").setValue(id);
                ref.child("toko").child(keyID).child("order").child(id).child("id_pelanggan").setValue(userID);
                ref.child("toko").child(keyID).child("order").child(id).child("nama_pelanggan").setValue(nama_pelanggan);
                ref.child("toko").child(keyID).child("order").child(id).child("barang").setValue(nInfo_barang);
                ref.child("toko").child(keyID).child("order").child(id).child("keluhan").setValue(nKeluhan);
                ref.child("toko").child(keyID).child("order").child(id).child("day").setValue(mDay);
                ref.child("toko").child(keyID).child("order").child(id).child("month").setValue(mMonth);
                ref.child("toko").child(keyID).child("order").child(id).child("year").setValue(mYear);
                ref.child("toko").child(keyID).child("order").child(id).child("hour").setValue(mHour);
                ref.child("toko").child(keyID).child("order").child(id).child("minute").setValue(mMinute);
                ref.child("toko").child(keyID).child("order").child(id).child("arrival").child("tanggal").child("day").setValue(nDay);
                ref.child("toko").child(keyID).child("order").child(id).child("arrival").child("tanggal").child("month").setValue(nMonth);
                ref.child("toko").child(keyID).child("order").child(id).child("arrival").child("tanggal").child("year").setValue(nYear);
                ref.child("toko").child(keyID).child("alert").setValue(true);

                dialog.show();
            }
        });

    }

    public void back(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
        finish();
    }
}
