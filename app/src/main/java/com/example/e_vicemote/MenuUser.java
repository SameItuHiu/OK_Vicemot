package com.example.e_vicemote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e_vicemote.Chat.Chat;
import com.example.e_vicemote.OpenService.OpenService;
import com.example.e_vicemote.Order.StatusOrder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuUser extends AppCompatActivity {

    private FirebaseAuth mAuth;

    TextView txt_order;
    ImageView ic_order;
    ImageView alert_order,alert_toko;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);

        final Button open_servis = findViewById(R.id.btn_buka_servis);
        final TextView nama_user = findViewById(R.id.nama_user);
        final TextView id = findViewById(R.id.id);
        final LinearLayout layout_mitra = findViewById(R.id.layout_mitra);

        txt_order = findViewById(R.id.txt_order);
        ic_order = findViewById(R.id.ic_order);
        alert_order = findViewById(R.id.alert_order);
        alert_toko = findViewById(R.id.alert_toko);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            userID = user.getUid();
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String text1 = dataSnapshot.child("account").child(userID).child("nama").getValue().toString();
                String status = dataSnapshot.child("account").child(userID).child("status").getValue().toString();

                if (status.equals("user") ){
                    open_servis.setVisibility(View.VISIBLE);
                } else{
                    layout_mitra.setVisibility(View.VISIBLE);
                }

                nama_user.setText(text1);
                id.setText(userID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Data", "Error: ", databaseError.toException());
            }
        });
    }

    public void maps(View view) {
        Intent intent = new Intent(MenuUser.this, Map.class);
        startActivity(intent);
        finish();
    }

    public void chat(View view) {
        Intent intent = new Intent(MenuUser.this, Chat.class);
        startActivity(intent);
        finish();
    }

    public void akun(View view) {
        //Intent intent = new Intent(MenuUser.this, user_account.class);
        //startActivity(intent);
        //finish();

    }

    public void buka_servis(View view) {

        Intent intent = new Intent(MenuUser.this, OpenService.class);
        startActivity(intent);
        finish();

    }

    public void logout(View view) {
        mAuth.signOut();
        Intent intent = new Intent(MenuUser.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void order(View view) {
        Intent intent = new Intent(MenuUser.this, StatusOrder.class);
        startActivity(intent);
        finish();
    }

    public void order_masuk(View view) {
        //Intent intent = new Intent(MenuUser.this, user_order_mitra.class);
        //startActivity(intent);
        //finish();
    }

}
