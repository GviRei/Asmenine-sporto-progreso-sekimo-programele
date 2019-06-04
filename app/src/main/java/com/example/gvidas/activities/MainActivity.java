package com.example.gvidas.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gvidas.Classes.LstViewAdapter;
import com.example.gvidas.activities.Profile.ProfileActivity;
import com.example.gvidas.activities.Profile.ProfileShowActivity;
import com.example.gvidas.activities.Progress.AdvancedVo2MaxActivity;
import com.example.gvidas.activities.Progress.CheckProgressActivity;
import com.example.gvidas.activities.Progress.OneRMActivity;
import com.example.gvidas.activities.Progress.StepCounterActivity;
import com.example.gvidas.activities.Progress.VO2MaxActivity;
import com.example.gvidas.activities.Workout.CreateExerciseActivity;
import com.example.gvidas.activities.Workout.CreateWorkoutActivity;
import com.example.gvidas.activities.Workout.ShowDataActivity;
import com.example.gvidas.activities.Workout.StartWorkoutActivity;
import com.example.gvidas.database.Exercise;
import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.sportapplication.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Context context;
    TextView text, txt;
    Button btDelete;
    RelativeLayout relative;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        setContentView(R.layout.activity_main);
        setTitle("Workout list");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dl = (DrawerLayout) findViewById(R.id.mainActivity);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.startWorkout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, StartWorkoutActivity.class);
                startActivity(myIntent);
            }
        });


        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.account) {
                    Intent myIntent = new Intent(MainActivity.this, ProfileShowActivity.class);
                    startActivity(myIntent);
                    return false;
                } else if (id == R.id.progress) {
                    Intent myIntent = new Intent(MainActivity.this, CheckProgressActivity.class);
                    startActivity(myIntent);
                    return false;
                } else if (id == R.id.counter) {
                    Intent myIntent = new Intent(MainActivity.this, StepCounterActivity.class);
                    startActivity(myIntent);
                    return false;
                } else if (id == R.id.createExercise) {
                    Intent myIntent = new Intent(MainActivity.this, CreateExerciseActivity.class);
                    startActivity(myIntent);
                    return false;
                } else if (id == R.id.simpleVo2) {
                    Intent myIntent = new Intent(MainActivity.this, VO2MaxActivity.class);
                    startActivity(myIntent);
                    return false;
                } else if (id == R.id.advancedVo222) {
                    Intent myIntent = new Intent(MainActivity.this, AdvancedVo2MaxActivity.class);
                    startActivity(myIntent);
                    return false;
                }  if (id == R.id.pressMax) {
                    Intent myIntent = new Intent(MainActivity.this, OneRMActivity.class);
                    startActivity(myIntent);
                    return false;
                } if (id == R.id.createWorkoutPlan) {
                    Intent myIntent = new Intent(MainActivity.this, CreateWorkoutActivity.class);
                    startActivity(myIntent);
                    return false;
                } if (id == R.id.stepCounter) {
                    Intent myIntent = new Intent(MainActivity.this, StepCounterActivity.class);
                    startActivity(myIntent);
                    return false;
                }
                return true;
            }
        });
        // final Button btDelete = (Button) findViewById(R.id.btDelete);


        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show sign up activity
            addDefaultExercises();
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();

        final ListView lstview = (ListView) findViewById(R.id.workoutListView);

        try {
            String[] items = getWorkoutNameAndDate();
            List<String> list = Arrays.asList(items);
            Collections.reverse(list);
            String[] items2 = (String[]) list.toArray();
            LstViewAdapter adapter = new LstViewAdapter(this, R.layout.list_item, R.id.txt, items2);
            lstview.setAdapter(adapter);
        } catch (Exception e) {
            return;
        }
        final String[] IDs = getWorkoutIDS();
        List<String> id = Arrays.asList(IDs);
        Collections.reverse(id);
        final String[] IDs2 = (String[]) id.toArray();

        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                txt = (TextView) view.findViewById(R.id.txt);
                // btDelete = (Button) lstview.findViewById(R.id.btDelete);
                Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
                intent.putExtra("workoutID", IDs2[position]);


                // btDelete.setId(Integer.parseInt(IDs[position]));
                startActivity(intent);
                //finish();
            }


        });


    }


    public void addDefaultExercises() {
        Random rand = new Random();
        int id = 0;
        String[] chestExercises = {"Bench press", "Incline press", "Flies", "Peck deck", "Chest Dips"};
        String[] legsExercises = {"Squats", "Leg press", "Lunges", "Hack Squat", "Machine Squat"};
        String[] backExercises = {"Deadlift", "Cable Row", "Dumbell Row", "Rowing Machine"};
        String[] bicepsExercises = {"Dumbell Curl", "Barbell Curl", "EZ-Bar Curl", "Zottman Curl"};
        String[] tricepsExercises = {"Rope Pushdown", "Triceps Dips", "Overhead Extension", "SkullCrusher"};
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        //Chest load
        for (int i = 0; i < chestExercises.length; i++) {
            id = rand.nextInt(1000 + 1);
            Exercise exercise = new Exercise(id, chestExercises[i], "Chest");
            dbHandler.addExercise(exercise);
        }
        //Leg load
        for (int i = 0; i < legsExercises.length; i++) {
            id = rand.nextInt(1000 + 1);
            Exercise exercise = new Exercise(id, legsExercises[i], "Legs");
            dbHandler.addExercise(exercise);
        }
        //Back load
        for (int i = 0; i < backExercises.length; i++) {
            id = rand.nextInt(1000 + 1);
            Exercise exercise = new Exercise(id, backExercises[i], "Back");
            dbHandler.addExercise(exercise);
        }
        //Biceps load
        for (int i = 0; i < bicepsExercises.length; i++) {
            id = rand.nextInt(1000 + 1);
            Exercise exercise = new Exercise(id, bicepsExercises[i], "Biceps");
            dbHandler.addExercise(exercise);
        }
        //Triceps load
        for (int i = 0; i < tricepsExercises.length; i++) {
            id = rand.nextInt(1000 + 1);
            Exercise exercise = new Exercise(id, tricepsExercises[i], "Triceps");
            dbHandler.addExercise(exercise);
        }
        // Exercise exercise = new Exercise(id, name, category);
        // dbHandler.addExercise(exercise);
    }


    public String[] getWorkoutIDS() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String ids = dbHandler.getAllWorkoutIds();
        String[] splitted = ids.split(" ");

        LinkedHashSet<String> array = new LinkedHashSet<String>(Arrays.asList(splitted));
        String[] noDuplicatesID = array.toArray(new String[array.size()]);
        int size = noDuplicatesID.length;
        return noDuplicatesID;
    }

    public String[] getWorkoutNameAndDate() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String[] IDS = getWorkoutIDS();
        int count = IDS.length;
        int id = 0;
        String data = "";
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            id = Integer.parseInt(IDS[i]);
            data = dbHandler.loadWorkoutDataForListView(id);
            result[i] = data;
        }
        return result;
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //toolbar selections
        if (id == R.id.action_profile) {
            Intent myIntent = new Intent(MainActivity.this, ProfileShowActivity.class);
            startActivity(myIntent);
            return false;
        } else if (id == R.id.action_calendar) {
            Intent myIntent = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(myIntent);
            return false;
        } else if (id == R.id.action_createWorkout) {
            Intent myIntent = new Intent(MainActivity.this, CreateWorkoutActivity.class);
            startActivity(myIntent);
            return false;
        } else if (id == R.id.action_createExercise) {
            Intent myIntent = new Intent(MainActivity.this, CreateExerciseActivity.class);
            startActivity(myIntent);
            return false;
        } else if (id == R.id.action_measureVO2Max) {
            Intent myIntent = new Intent(MainActivity.this, VO2MaxActivity.class);
            startActivity(myIntent);
            return false;
        } else if (id == R.id.check_progress) {
            Intent myIntent = new Intent(MainActivity.this, CheckProgressActivity.class);
            startActivity(myIntent);
            return false;
        }  if (id == R.id.advancedvo2) {
            Intent myIntent = new Intent(MainActivity.this, AdvancedVo2MaxActivity.class);
            startActivity(myIntent);
            return false;
        } if (id == R.id.calculate1RM) {
            Intent myIntent = new Intent(MainActivity.this, OneRMActivity.class);
            startActivity(myIntent);
            return false;
        } if (id == R.id.stepCounter) {
            Intent myIntent = new Intent(MainActivity.this, StepCounterActivity.class);
            startActivity(myIntent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (t.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
