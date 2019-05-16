package com.example.gvidas.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.sportapplication.R;

public class ProfileShowActivity extends AppCompatActivity {

    TextView name, age, height, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name = (TextView) findViewById(R.id.profileName);
        age = (TextView) findViewById(R.id.profileAge);
        height = (TextView) findViewById(R.id.profileHeight);
        weight = (TextView) findViewById(R.id.profileWeight);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadProfile();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            //ends activity
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadProfile() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String str = dbHandler.loadProfileData();
        String[] splitted = str.split("\\s+");
        name.setText(splitted[0]);
        age.setText(splitted[1]);
        height.setText(splitted[2]);
        weight.setText(splitted[3]);

    }

}
