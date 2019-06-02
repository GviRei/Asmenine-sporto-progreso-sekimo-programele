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

public class ShowDataActivity extends AppCompatActivity {

    int workoutID;
    TextView textView, workoutTitle, comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        textView = (TextView)findViewById(R.id.showDataText);
        workoutTitle = (TextView) findViewById(R.id.workoutTitle);
        comments = (TextView) findViewById(R.id.comment);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String ID = extras.getString("workoutID");
            workoutID = Integer.parseInt(ID);
        }

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String data = dbHandler.loadAllDataFromWorkout(workoutID);
        String title = dbHandler.loadWorkoutTitle(workoutID);
        String comment = dbHandler.loadWorkoutComment(workoutID);
        textView.setText(data);
        workoutTitle.setText(title);
        comments.setText(comment);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item .getItemId();

        if (id == android.R.id.home)  {
            //ends activity
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
