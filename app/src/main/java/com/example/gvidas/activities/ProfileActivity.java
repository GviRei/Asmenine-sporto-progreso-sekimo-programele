package com.example.gvidas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gvidas.database.Exercise;
import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.database.Profile;
import com.example.gvidas.sportapplication.R;

import java.util.Random;

public class ProfileActivity extends AppCompatActivity {

    TextView list;
    EditText profileName, profileAge, profileWeight, profileHeight;

     Button buttonSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //list = (TextView) findViewById(R.id.listProfile);
        profileName = (EditText) findViewById(R.id.profileNameTextField);
        profileAge = (EditText) findViewById(R.id.profileAgeTextField);
        profileHeight = (EditText) findViewById(R.id.profileHeightTextField);
        profileWeight = (EditText) findViewById(R.id.profileWeightTextField);
        buttonSave = (Button) findViewById(R.id.saveProfile);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProfile();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //Back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public void addProfile() {
        Random randId = new Random();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        // int id = Integer.parseInt(random.getText().toString());
        int id = randId.nextInt(1000 + 1);
        String name = profileName.getText().toString();
        int age = Integer.parseInt(profileAge.getText().toString());
        int height = Integer.parseInt(profileHeight.getText().toString());
        int weight = Integer.parseInt(profileWeight.getText().toString());
        Profile profile = new Profile(id, name, age, height, weight);
        dbHandler.addProfile(profile);
        profileName.setText("");
        profileAge.setText("");
        profileHeight.setText("");
        profileWeight.setText("");
    }

    public void loadProfile(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        list.setText(dbHandler.loadProfile());
        profileName.setText("");
        profileAge.setText("");
        profileHeight.setText("");
        profileWeight.setText("");
    }
}
