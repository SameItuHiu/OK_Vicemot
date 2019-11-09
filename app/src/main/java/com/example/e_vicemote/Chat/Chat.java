package com.example.e_vicemote.Chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.e_vicemote.MenuUser;
import com.example.e_vicemote.R;

public class Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuUser.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public void back(View view) {
        Intent intent = new Intent(this, MenuUser.class);
        startActivity(intent);
        finish();
    }
}
