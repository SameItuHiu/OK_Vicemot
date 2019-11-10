package com.example.e_vicemote.Order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.e_vicemote.Adapter.CustomAdapter3;
import com.example.e_vicemote.Model.Rincian;
import com.example.e_vicemote.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailOrder extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String id_order,userID,id_mitra;
    BottomSheetDialog dialog;
    DatabaseReference ref;
    String checked,key;

    int total = 0;
    TextView total_harga;

    ListView list_harga;
    List<Rincian> listdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        list_harga = (ListView) findViewById(R.id.listharga);

        Intent intent = getIntent();
        id_order = intent.getStringExtra(StatusOrder.ORDER_ID);
        total_harga = findViewById(R.id.total_harga);
        final TextView status = findViewById(R.id.status);
        final TextView order_dibuat = findViewById(R.id.order_dibuat);
        final TextView barang = findViewById(R.id.barang);
        final TextView keluhan = findViewById(R.id.keluhan);
        final TextView nama_toko = findViewById(R.id.nama_toko);
        final TextView catatan_mitra = findViewById(R.id.catatan_mitra);
        final TextView alamat_toko = findViewById(R.id.alamat_toko); //

        final ListView listharga = findViewById(R.id.listharga);
        final Button alamat = findViewById(R.id.alamat);
        final Button finish = findViewById(R.id.finish);
        final TextView like_dislike = findViewById(R.id.like_dislike);
        final TextView txt_komentar = findViewById(R.id.txt_komentar);

        final LinearLayout mLayout = findViewById(R.id.layout2);
        final LinearLayout layout_feedback = findViewById(R.id.layout_feedback);

        //User auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("account").child(userID).child("order");
        databaseReference.orderByChild("id_order")
                .equalTo(id_order)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot s : dataSnapshot.getChildren()){
                            Integer nDay,nMonth,nYear,nHour,nMinute;
                            String mstatus,mbarang,mkeluhan,mnama_toko,mcatatan_mitra;

                            mstatus = s.child("status_order").getValue().toString();
                            mbarang = s.child("info_barang").getValue().toString();
                            mkeluhan = s.child("keluhan").getValue().toString();
                            mnama_toko = s.child("nama_toko").getValue().toString();
                            id_mitra = s.child("id_mitra").getValue().toString();
                            key = s.getKey();

                            nDay = s.child("order_dibuat").child("day").getValue(Integer.class);
                            nMonth = s.child("order_dibuat").child("month").getValue(Integer.class);
                            nYear = s.child("order_dibuat").child("year").getValue(Integer.class);
                            nHour = s.child("order_dibuat").child("hour").getValue(Integer.class);
                            nMinute = s.child("order_dibuat").child("minute").getValue(Integer.class);

                            status.setText(mstatus);
                            order_dibuat.setText(nHour + ":" + nMinute + " WIB "+" / " + nDay + "-" + nMonth + "-" + nYear);
                            barang.setText(mbarang);
                            keluhan.setText(mkeluhan);
                            nama_toko.setText(mnama_toko);

                            if (mstatus.equals("diterima")){
                                mLayout.setVisibility(View.VISIBLE);
                                mcatatan_mitra = s.child("catatan_mitra").getValue().toString();
                                catatan_mitra.setText(mcatatan_mitra);
                                list_harga_barang();

                            }else if (mstatus.equals("ditolak")){
                                startActivity(new Intent(DetailOrder.this, StatusOrder.class));
                                finish();

                            }else if(mstatus.equals("selesai")){
                                alamat.setVisibility(View.GONE);
                                finish.setVisibility(View.GONE);
                                mLayout.setVisibility(View.VISIBLE);
                                layout_feedback.setVisibility(View.VISIBLE);
                                list_harga_barang();

                                String mlike_dislike = s.child("feedback").child("status").getValue().toString();
                                String mfeedback = s.child("feedback").child("komentar").getValue().toString();

                                like_dislike.setText(mlike_dislike);
                                txt_komentar.setText(mfeedback);

                                mcatatan_mitra = s.child("catatan_mitra").getValue().toString();
                                catatan_mitra.setText(mcatatan_mitra);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Data", "Error: ", databaseError.toException());
                    }
                });

        ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String provinsi,kota,alamat1,semua;

                provinsi = dataSnapshot.child("toko").child(id_mitra).child("alamat").child("provinsi").getValue().toString();
                kota = dataSnapshot.child("toko").child(id_mitra).child("alamat").child("kota").getValue().toString();
                alamat1 = dataSnapshot.child("toko").child(id_mitra).child("alamat").child("alamat").getValue().toString();
                semua = alamat1+"," + kota+"," +provinsi;
                alamat_toko.setText(semua);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Jika orderan selesai (muncul feedback)
        View view = getLayoutInflater().inflate(R.layout.popup_feedback, null);
        final EditText txt_feedback = view.findViewById(R.id.txt_feedback);
        final ImageView img_like = view.findViewById(R.id.img_like);
        final ImageView img_dislike = view.findViewById(R.id.img_dislike);
        final ImageView img_like_checked = view.findViewById(R.id.img_like_checked);
        final ImageView img_dislike_checked = view.findViewById(R.id.img_dislike_checked);

        img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_like_checked.setVisibility(View.VISIBLE);
                img_dislike_checked.setVisibility(View.GONE);
                img_dislike.setVisibility(View.VISIBLE);
                img_like.setVisibility(View.GONE);
            }
        });
        img_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_dislike_checked.setVisibility(View.VISIBLE);
                img_like.setVisibility(View.VISIBLE);
                img_dislike.setVisibility(View.GONE);
                img_like_checked.setVisibility(View.GONE);

            }
        });

        Button btn_feedback = view.findViewById(R.id.btn_feedback);

        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mFeedback = txt_feedback.getText().toString();
                if (img_like_checked.getVisibility() == View.VISIBLE){
                    checked = "like";

                }else if (img_dislike_checked.getVisibility() == View.VISIBLE){
                    checked = "dislike";
                }

                FirebaseDatabase.getInstance().getReference().child("toko").child(id_mitra).child("order").child(id_order).child("status_order").setValue("selesai");
                FirebaseDatabase.getInstance().getReference().child("account").child(userID).child("order").child(id_order).child("status_order").setValue("selesai");

                FirebaseDatabase.getInstance().getReference().child("toko").child(id_mitra).child("order").child(id_order).child("feedback").child("status").setValue(checked);
                FirebaseDatabase.getInstance().getReference().child("account").child(userID).child("order").child(id_order).child("feedback").child("status").setValue(checked);

                FirebaseDatabase.getInstance().getReference().child("toko").child(id_mitra).child("order").child(id_order).child("feedback").child("komentar").setValue(mFeedback);
                FirebaseDatabase.getInstance().getReference().child("account").child(userID).child("order").child(id_order).child("feedback").child("komentar").setValue(mFeedback);

                dialog.dismiss();
                finish();
                startActivity(getIntent());

            }
        });
    }

    public void back(View view) {
        startActivity(new Intent(this, StatusOrder.class));
        finish();
    }

    public void google_maps(View view) {

    }

    public void selesai(View view) {
        dialog.show();
    }

    public void list_harga_barang(){
        listdata = new ArrayList<>();
        //attaching value event listener
        FirebaseDatabase.getInstance().getReference()
                .child("account").child(userID).child("order").child(key)
                .child("rincian_harga").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                listdata.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting data
                    Rincian data = postSnapshot.getValue(Rincian.class);
                    //adding data to the list
                    listdata.add(data);

                    Integer harga = Integer.valueOf(data.getHarga());
                    Integer jumlah = Integer.valueOf(data.getJumlah());
                    total = total + harga * jumlah;
                    String value = String.valueOf(total);
                    total_harga.setText(value);
                }

                //creating adapter
                CustomAdapter3 artistAdapter = new CustomAdapter3(DetailOrder.this, listdata);
                //attaching adapter to the listview
                list_harga.setAdapter(artistAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StatusOrder.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
