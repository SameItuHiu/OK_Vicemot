package com.example.e_vicemote.SettingAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.e_vicemote.Account.EditAccount;
import com.example.e_vicemote.R;

public class EditEmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);
    }
    public void onBackPressed() {
        Intent intent = new Intent(this, EditAccount.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
