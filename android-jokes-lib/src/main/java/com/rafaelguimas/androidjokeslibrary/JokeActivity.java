package com.rafaelguimas.androidjokeslibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "extra_joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        String joke = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            joke = extras.getString(EXTRA_JOKE);
        }

        TextView tvJoke = findViewById(R.id.tv_joke);
        tvJoke.setText(joke);
    }
}
