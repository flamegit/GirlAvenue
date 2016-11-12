package com.flame.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LadyViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new GirlPageFragment())
                    .commit();
        }

    }
}
