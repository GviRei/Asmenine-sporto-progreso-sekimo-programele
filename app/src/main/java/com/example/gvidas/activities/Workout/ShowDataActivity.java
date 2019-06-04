package com.example.gvidas.activities.Workout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gvidas.activities.MainActivity;
import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.sportapplication.R;

public class ShowDataActivity extends AppCompatActivity {

    int workoutID;
    TextView textView, workoutTitle, comments;
    Button delete;
    ProgressBar bar, bar2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        textView = (TextView)findViewById(R.id.showDataText);
        workoutTitle = (TextView) findViewById(R.id.workoutTitle);
        comments = (TextView) findViewById(R.id.comment);
        delete = (Button) findViewById(R.id.delete);
        bar = (ProgressBar) findViewById(R.id.bar1);
        bar2 = (ProgressBar) findViewById(R.id.bar23);
        setSupportActionBar(toolbar);
        setTitle("Workout Information");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String ID = extras.getString("workoutID");
            workoutID = Integer.parseInt(ID);
        }

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String data = dbHandler.loadAllDataFromWorkout(workoutID);
        String title = dbHandler.loadWorkoutTitle(workoutID);
        String comment = dbHandler.loadWorkoutComment(workoutID);
        String tiredness = dbHandler.loadTiredness(workoutID);
        String[] level1 = tiredness.split(" ");
        bar.setProgress(Integer.parseInt(level1[0]));
        bar2.setProgress(Integer.parseInt(level1[1]));
        textView.setText(data);
        workoutTitle.setText(title);
        comments.setText(comment);
        /*delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.deleteWorkout(workoutID);
                Intent intent = new Intent(ShowDataActivity.this, MainActivity.class);
                finish();
            }
        });*/

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void deleteWorkout(View view){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.deleteWorkout(workoutID);
        Intent intent = new Intent(ShowDataActivity.this, MainActivity.class);
        startActivity(intent);
        //finish();
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
