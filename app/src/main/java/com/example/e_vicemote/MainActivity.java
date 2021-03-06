package com.example.e_vicemote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.e_vicemote.Adapter.ViewPagerAdapter;
import com.example.e_vicemote.Intro.Intro;
import com.example.e_vicemote.Intro.Intro1;
import com.example.e_vicemote.Intro.Intro2;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

public class MainActivity extends AppCompatActivity {
    SpringDotsIndicator dotsIndicator;
    ViewPager mPager;
    Intro intro3;
    Intro1 intro2;
    Intro2 intro1;

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dotsIndicator = (SpringDotsIndicator) findViewById(R.id.spring_dots_indicator);
        mPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mPager);
        dotsIndicator.setViewPager(mPager);
        dotsIndicator.setDotsClickable(true);

    }

    public void login(View view) {
        Intent intent = new Intent(MainActivity.this,Login.class);
        startActivity(intent);
        finish();
    }

    public void create_account(View view) {
        Intent intent = new Intent(MainActivity.this, Sign_up.class);
        startActivity(intent);
        finish();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        intro1 = new Intro2();
        intro2 = new Intro1();
        intro3 = new Intro();
        viewPagerAdapter.addFragment(intro1);
        viewPagerAdapter.addFragment(intro2);
        viewPagerAdapter.addFragment(intro3);
        viewPager.setAdapter(viewPagerAdapter);
    }
}
