package com.example.e_vicemote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (mAuth.getCurrentUser() != null) {
                        // User is signed in (getCurrentUser() will be null if not signed in)
                        Intent intent = new Intent(SplashScreen.this, MenuUser.class);
                        startActivity(intent);
                        finish();

                    }else {
                        Intent obj = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(obj);
                        finish();
                    }

                }
            }

        };
        th.start();

    }
}
