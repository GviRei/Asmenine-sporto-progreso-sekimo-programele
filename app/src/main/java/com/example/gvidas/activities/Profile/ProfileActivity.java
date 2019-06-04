package com.example.gvidas.activities.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gvidas.activities.MainActivity;
import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.database.Profile;
import com.example.gvidas.sportapplication.R;

import java.util.Random;

public class ProfileActivity extends AppCompatActivity {

    EditText profileName, profileAge, profileWeight, profileHeight;
    Spinner spinner2;

    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        profileName = (EditText) findViewById(R.id.profileNameTextField);
        profileAge = (EditText) findViewById(R.id.profileAgeTextField);
        profileHeight = (EditText) findViewById(R.id.profileHeightTextField);
        profileWeight = (EditText) findViewById(R.id.profileWeightTextField);
        buttonSave = (Button) findViewById(R.id.saveProfile);
        spinner2 = (Spinner) findViewById(R.id.profileGenderSpinner);
        setTitle("Profile");

        String[] spinnerLists2 = {"Male", "Female"};
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_item, spinnerLists2);
        spinner2.setAdapter(spinnerAdapter2);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProfile();

            }
        });


    }


    public void addProfile() {
        Random randId = new Random();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int id = randId.nextInt(1000 + 1);
        if (profileName.getText().toString().trim().length() == 0 || profileAge.getText().toString().trim().length() == 0
                || profileHeight.getText().toString().trim().length() == 0 || profileWeight.getText().toString().trim().length() == 0) {
            Toast.makeText(ProfileActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String regexString = "^[0-9]*$";
            if (profileName.getText().toString().trim().matches(regexString)) {
                Toast.makeText(ProfileActivity.this, "Please enter valid name", Toast.LENGTH_SHORT).show();
                return;
            } else {
                String name = profileName.getText().toString();
                int age = Integer.parseInt(profileAge.getText().toString());
                int height = Integer.parseInt(profileHeight.getText().toString());
                int weight = Integer.parseInt(profileWeight.getText().toString());
                String gender = spinner2.getSelectedItem().toString();
                Profile profile = new Profile(id, name, age, gender, height, weight);
                dbHandler.addProfile(profile);
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }

    }

}
