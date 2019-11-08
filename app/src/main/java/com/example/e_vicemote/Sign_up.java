package com.example.e_vicemote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_up extends AppCompatActivity {

    private EditText email,password,verifpass,nama,phone;
    private RadioGroup radioGroup_gender;
    private RadioButton mradioButton;

    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        verifpass = findViewById(R.id.retype);
        nama = findViewById(R.id.fullname);
        phone = findViewById(R.id.no_handphone);
        radioGroup_gender = findViewById(R.id.radiogender);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("account");

    }

    public void back(View view) {
        Intent intent = new Intent(Sign_up.this, MainActivity.class);
        startActivity(intent);
    }

    public void signup(View view) {

        final String emailUser = email.getText().toString().trim();
        String passwordUser = password.getText().toString().trim();
        final String mPhone = phone.getText().toString().trim();
        final String mNama = nama.getText().toString().trim();
        String verif = verifpass.getText().toString().trim();

        // get selected radio button from radioGroup
        int select = radioGroup_gender.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        mradioButton = (RadioButton) findViewById(select);

        if (mNama.isEmpty()){
            nama.setError("Nama tidak boleh kosong");

            // jika email not valid
        }else if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()){
            email.setError("Email tidak valid");

            // jika email kosong
        }else if (emailUser.isEmpty()){
            email.setError("Email tidak boleh kosong");

            // jika nomor handphone kosong
        }else if (mPhone.isEmpty()){
            phone.setError("Nomor handphone tidak boleh kosong");
        }

        // jika password kosong
        else if (passwordUser.isEmpty()){
            password.setError("Password tidak boleh kosong");
        }

        //jika password kurang dari 6 karakter
        else if (passwordUser.length() < 6){
            password.setError("Password minimal terdiri dari 6 karakter");

            // jika penulisan password tidak sama
        }else if (!passwordUser.equals(verif)){
            verifpass.setError("Password tidak sama");

        }else {

            final AlertDialog.Builder mBuiler = new AlertDialog.Builder(Sign_up.this);
            final View mView = getLayoutInflater().inflate(R.layout.popup,null);
            mBuiler.setView(mView);
            final AlertDialog dialog = mBuiler.create();
            dialog.show();

            //create user dengan firebase auth
            mAuth.createUserWithEmailAndPassword(emailUser,passwordUser)
                    .addOnCompleteListener(Sign_up.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //jika gagal register do something
                            if (!task.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(Sign_up.this,
                                        "Signup fail"+ task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }else {
                                FirebaseUser user =  mAuth.getCurrentUser();
                                String userId = user.getUid();
                                user.sendEmailVerification();
                                String gender = mradioButton.getText().toString();
                                ref.child(userId).child("nama").setValue(mNama);
                                ref.child(userId).child("email").setValue(emailUser);
                                ref.child(userId).child("gender").setValue(gender);
                                ref.child(userId).child("No_Handphone").setValue(mPhone);
                                ref.child(userId).child("status").setValue("user");
                                dialog.dismiss();

                                Toast.makeText(Sign_up.this,
                                        "Register berhasil, Cek email untuk melakukan verifikasi",
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Sign_up.this,MainActivity.class));
                                finish();
                            }
                        }
                    });
        }
    }


    public void signin(View view) {
        Intent intent = new Intent(Sign_up.this, Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
