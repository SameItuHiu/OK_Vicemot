package com.example.e_vicemote.OpenService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_vicemote.MenuUser;
import com.example.e_vicemote.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class OpenService4 extends AppCompatActivity {

    Uri path_image;
    String path_image_storage;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_service4);

        final ImageView image_selected = findViewById(R.id.image_photo);
        RelativeLayout select = findViewById(R.id.select_photo);
        Button finish = findViewById(R.id.finish);

        storage = FirebaseStorage.getInstance();
        storageReference =  storage.getReference();

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        image_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });



        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String userID = user.getUid();

                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                        .child("pendaftaran").child(userID);

                final StorageReference ref1 = storageReference.child(UUID.randomUUID().toString());
                if (path_image != null){

//                    ref1.putFile(path_image)
//                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                                    ref1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            path_image_storage = uri.toString();
//                                            ref.child("path_image").setValue(path_image_storage);
//                                        }
//                                    });
//
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(OpenService4.this, "Failed", Toast.LENGTH_SHORT).show();
//                                }
//                            });

                    Bundle bundle = getIntent().getExtras();
                    String nama_toko = bundle.getString("nama_toko");
                    String layanan = bundle.getString("layanan");
                    String provinsi = bundle.getString("provinsi");
                    String kota = bundle.getString("kota");
                    String alamat = bundle.getString("alamat");
                    String Latitut = bundle.getString("Latitut");
                    String Longitut = bundle.getString("Longitut");
                    String bjam = bundle.getString("bjam");
                    String bmenit = bundle.getString("bmenit");
                    String tjam = bundle.getString("tjam");
                    String tmenit = bundle.getString("tmenit");

                    ref.child("nama_toko").setValue(nama_toko);
                    ref.child("layanan").setValue(layanan);
                    ref.child("alamat").child("provinsi").setValue(provinsi);
                    ref.child("alamat").child("kota").setValue(kota);
                    ref.child("alamat").child("alamat").setValue(alamat);
                    ref.child("alamat").child("kordinat").child("latitude").setValue(Latitut);
                    ref.child("alamat").child("kordinat").child("longitude").setValue(Longitut);
                    ref.child("jadwal").child("buka").child("jam").setValue(bjam);
                    ref.child("jadwal").child("buka").child("menit").setValue(bmenit);
                    ref.child("jadwal").child("tutup").child("jam").setValue(tjam);
                    ref.child("jadwal").child("tutup").child("menit").setValue(tmenit);
                    ref.child("IdMitra").setValue(userID);

                    FirebaseDatabase.getInstance().getReference()
                            .child("account").child(userID).child("status").setValue("Pending");


                }else{
                    Toast.makeText(OpenService4.this, "Foto belum diupload", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(OpenService4.this, MenuUser.class));
                finish();
                Toast.makeText(OpenService4.this, "Registrasi berhasil, sedang menunggu proses verifikasi admin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void back(View view) {
        startActivity(new Intent(OpenService4.this, OpenService3.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, OpenService3.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imgPoster = findViewById(R.id.image_photo);
        RelativeLayout select = findViewById(R.id.select_photo);
        if (resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();

            Glide.with(this).load(imageUri).into(imgPoster);

            select.setVisibility(View.GONE);
            imgPoster.setVisibility(View.VISIBLE);

            path_image = data.getData();

        }

    }
}
