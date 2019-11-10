package com.example.e_vicemote.FragmentAcount;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class User extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference ref;

    String userID;

    public User() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        final TextView nama_user = v.findViewById(R.id.nama_user);
        final TextView gender = v.findViewById(R.id.gender);
        final TextView contact = v.findViewById(R.id.nomor);
        final TextView email = v.findViewById(R.id.email);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("account").child(userID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String mNama = dataSnapshot.child("nama").getValue().toString();
                String mEmail = dataSnapshot.child("email").getValue().toString();
                String mGender = dataSnapshot.child("gender").getValue().toString();
                String mContact = dataSnapshot.child("No_Handphone").getValue().toString();

                nama_user.setText(mNama);
                email.setText(mEmail);
                gender.setText(mGender);
                contact.setText(mContact);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

}
