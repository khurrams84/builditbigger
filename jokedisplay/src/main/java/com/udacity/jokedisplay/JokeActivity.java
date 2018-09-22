package com.udacity.jokedisplay;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class JokeActivity extends AppCompatActivity {

    public static String EXTRA_JOKE = "extra_joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_JOKE, getIntent().getStringExtra(EXTRA_JOKE));

            JokeFragment frag = new JokeFragment();
            frag.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, frag)
                    .commit();
        }
    }

}
