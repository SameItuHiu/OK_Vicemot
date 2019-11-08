package com.example.e_vicemote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private EditText email,password;
    private FirebaseAuth mAuth;
    FirebaseDatabase db;
    FirebaseUser user;
    TextView txt_peringatan;
    EditText mEmail;

    BottomSheetDialog dialog2,dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        View view1 = getLayoutInflater().inflate(R.layout.popup_peringatan, null);
        txt_peringatan = view1.findViewById(R.id.txt_peringatan);
        dialog1 = new BottomSheetDialog(this);
        dialog1.setContentView(view1);

        View view = getLayoutInflater().inflate(R.layout.popup_reset_password, null);
        mEmail = view.findViewById(R.id.email);
        Button kirim = view.findViewById(R.id.kirim);
        dialog2 = new BottomSheetDialog(this);
        dialog2.setContentView(view);

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mEmail.getText().toString();
                if (mail.isEmpty()){
                    txt_peringatan.setText("Email tidak boleh kosong");
                    dialog1.show();

                }else{
                    mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Login.this,"Please check your email",Toast.LENGTH_SHORT).show();
                                dialog2.dismiss();
                            }
                        }
                    });
                }
            }
        });

    }

    public void login(View view) {

        //menampung inputan user
        final String emailUser = email.getText().toString().trim();
        final String passwordUser = password.getText().toString().trim();

        //validasi email dan password
        // jika email kosong
        if (emailUser.isEmpty()) {
            email.setError("Email tidak boleh kosong");
        }
        // jika email not valid
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()) {
            email.setError("Email tidak valid");
        }
        // jika password kosong
        else if (passwordUser.isEmpty()) {
            password.setError("Password tidak boleh kosong");
        }
        //jika password kurang dari 6 karakter
        else if (passwordUser.length() < 6) {
            password.setError("Password minimal terdiri dari 6 karakter");
        } else {

            final AlertDialog.Builder mBuiler = new AlertDialog.Builder(Login.this);
            final View mView = getLayoutInflater().inflate(R.layout.popup,null);
            mBuiler.setView(mView);
            final AlertDialog dialog = mBuiler.create();
            dialog.show();

            mAuth.signInWithEmailAndPassword(emailUser, passwordUser)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            user =  mAuth.getCurrentUser();
                            // ketika gagal login maka akan do something
                            if (!task.isSuccessful()) {
                                Toast.makeText(Login.this,
                                        "Failed login " + task.getException().getMessage()
                                        , Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            } else if (user != null && user.isEmailVerified()){
                                //if(user.isEmailVerified()){
                                dialog.dismiss();
                                Intent intent = new Intent(Login.this, MenuUser.class);
                                startActivity(intent);
                                finish();
                                //}

                            }else {
                                Toast.makeText(Login.this,
                                        "Cek email untuk melakukan verifikasi"
                                        , Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }


                    });
        }

    }
    public void back(View view) {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void forgot(View view) {
        dialog2.show();

    }

    public void signup(View view) {
        Intent intent = new Intent(Login.this, Sign_up.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
