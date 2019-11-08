package com.example.e_vicemote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OpenService extends AppCompatActivity {

    TextView Toko,txt_salam;
    String userID,nama;
    DatabaseReference ref;
    Spinner Layanan;
    private Context context;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuUser.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_service);
        context = this;

        Toko = findViewById(R.id.nama_toko);
        Layanan = findViewById(R.id.Spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Layanan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Layanan.setAdapter(adapter);

        txt_salam = findViewById(R.id.salam);
        Button finish = findViewById(R.id.finish);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        ref = FirebaseDatabase.getInstance().getReference().child("account").child(userID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama = dataSnapshot.child("nama").getValue().toString();
                txt_salam.setText("Hello " + nama);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Data", "Error: ", databaseError.toException());
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mToko = Toko.getText().toString();
                final String mLayanan = String.valueOf(Layanan.getSelectedItem());

                View view = getLayoutInflater().inflate(R.layout.popup_peringatan, null);
                final TextView txt_peringatan = view.findViewById(R.id.txt_peringatan);
                final BottomSheetDialog dialog = new BottomSheetDialog(context);
                dialog.setContentView(view);

                if (mToko.isEmpty()){
                    txt_peringatan.setText("Nama toko tidak boleh kosong");
                    dialog.show();
                }else if (mLayanan.isEmpty()){
                    txt_peringatan.setText("Layanan toko tidak boleh kosong");
                    dialog.show();
                }else{

                    Bundle bundle = new Bundle();
                    bundle.putString("nama_toko", mToko);
                    bundle.putString("layanan", mLayanan);
                    Intent intent = new Intent(OpenService.this, OpenService2.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    public void back(View view) {
        startActivity(new Intent(OpenService.this, MenuUser.class));
        finish();
    }
}
