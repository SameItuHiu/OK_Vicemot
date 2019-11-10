package com.example.e_vicemote.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.example.e_vicemote.Adapter.TabPagerAdapter;
import com.example.e_vicemote.FragmentAcount.Mitra;
import com.example.e_vicemote.FragmentAcount.User;
import com.example.e_vicemote.MenuUser;
import com.example.e_vicemote.R;
import com.google.android.material.tabs.TabLayout;

public class MainAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_account);

        // find views by id
        ViewPager vPager = findViewById(R.id.pager);
        TabLayout tLayout = findViewById(R.id.tablayout);

        // attach tablayout with viewpager
        tLayout.setupWithViewPager(vPager);

        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());

        // add your fragments
        adapter.addFrag(new User(), "User");
        adapter.addFrag(new Mitra(), "Mitra");

        // set adapter on viewpager
        vPager.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuUser.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public void back(View view) {
        Intent intent = new Intent(MainAccount.this, MenuUser.class);
        startActivity(intent);
        finish();
    }

    public void setting(View view) {
        Intent intent = new Intent(MainAccount.this, EditAccount.class);
        startActivity(intent);
        finish();
    }
}
