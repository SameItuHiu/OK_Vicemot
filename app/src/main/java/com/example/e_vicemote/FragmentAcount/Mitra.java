package com.example.e_vicemote.FragmentAcount;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e_vicemote.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class Mitra extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference ref;

    String userID,Dare;

    LinearLayout layout1, layout2;

    public Mitra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mitra, container, false);

        final TextView nama_toko = v.findViewById(R.id.nama_toko);
        final TextView layanan = v.findViewById(R.id.layanan);
        final TextView buka = v.findViewById(R.id.buka);

        final TextView provinsi = v.findViewById(R.id.provinsi);
        final TextView kota = v.findViewById(R.id.kota);
        final TextView alamat = v.findViewById(R.id.alamat);

        layout1 = v.findViewById(R.id.layout1);
        layout2 = v.findViewById(R.id.layout2);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        ref = FirebaseDatabase.getInstance().getReference();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Dare = dataSnapshot.child("account").child(userID).child("status").getValue().toString();
                if (Dare.equals("mitra")){
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.GONE);

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String mToko = dataSnapshot.child("toko").child(userID).child("nama_toko").getValue().toString();
                            String mLayanan = dataSnapshot.child("toko").child(userID).child("layanan").getValue().toString();
                            String mBuka_jam = dataSnapshot.child("toko").child(userID).child("jadwal").child("buka").child("jam").getValue().toString();
                            String mBuka_menit = dataSnapshot.child("toko").child(userID).child("jadwal").child("buka").child("menit").getValue().toString();
                            String mTutup_jam = dataSnapshot.child("toko").child(userID).child("jadwal").child("tutup").child("jam").getValue().toString();
                            String mTutup_menit = dataSnapshot.child("toko").child(userID).child("jadwal").child("tutup").child("menit").getValue().toString();

                            String mProvinsi = dataSnapshot.child("toko").child(userID).child("alamat").child("provinsi").getValue().toString();
                            String mKota = dataSnapshot.child("toko").child(userID).child("alamat").child("kota").getValue().toString();
                            String mAlamat = dataSnapshot.child("toko").child(userID).child("alamat").child("alamat").getValue().toString();

                            nama_toko.setText(mToko);
                            layanan.setText(mLayanan);
                            buka.setText(mBuka_jam + ":" + mBuka_menit+ "WIB" + " ~ " + mTutup_jam + ":" + mTutup_menit + "WIB");

                            provinsi.setText(mProvinsi);
                            kota.setText(mKota);
                            alamat.setText(mAlamat);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else {
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

}
