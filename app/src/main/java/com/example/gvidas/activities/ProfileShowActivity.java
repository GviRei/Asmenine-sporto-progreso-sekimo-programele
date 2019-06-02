package com.example.gvidas.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.sportapplication.R;

public class ProfileShowActivity extends AppCompatActivity {

    EditText name;
    EditText age ;
    EditText height;
    EditText weight ;
    ImageView edit;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name = (EditText) findViewById(R.id.profileName);
        age = (EditText) findViewById(R.id.profileAge);
        height = (EditText) findViewById(R.id.profileHeight);
        weight = (EditText) findViewById(R.id.profileWeight);
        edit = (ImageView) findViewById(R.id.editImageView);
        save = (Button) findViewById(R.id.editProfile);
        //String namas = name.getText().
        save.setEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadProfile();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile(name.getText().toString(), Integer.parseInt(age.getText().toString()),
                        Integer.parseInt(height.getText().toString()),
                        Integer.parseInt(weight.getText().toString()) );
                Toast.makeText(ProfileShowActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    public void editProfile() {
        name.setFocusableInTouchMode(true);
        age.setFocusableInTouchMode(true);
        height.setFocusableInTouchMode(true);
        weight.setFocusableInTouchMode(true);
        name.setFocusable(true);
        age.setFocusable(true);
        height.setFocusable(true);
        weight.setFocusable(true);
        save.setClickable(true);
        save.setEnabled(true);

    }


    public void saveProfile(String name, int age, int height, int weight) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int id = dbHandler.getProfileID();
        dbHandler.updateProfile(id, name, age, height, weight);

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
