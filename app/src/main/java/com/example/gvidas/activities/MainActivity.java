package com.example.gvidas.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gvidas.Classes.LstViewAdapter;
import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.sportapplication.R;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class MainActivity extends AppCompatActivity {

    Context context;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        text = (TextView) findViewById(R.id.mainText);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.startWorkout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, StartWorkoutActivity.class);
                startActivity(myIntent);
            }
        });

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show sign up activity
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            Toast.makeText(MainActivity.this, "Run only once", Toast.LENGTH_LONG)
                    .show();
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

        ListView lstview=(ListView)findViewById(R.id.workoutListView);
        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(context, "An item of the ListView is clicked.", Toast.LENGTH_LONG).show();
            }
        });

        String[] items={"1","2","3","4","5"};
        LstViewAdapter adapter=new LstViewAdapter(this,R.layout.list_item,R.id.txt,items);
        // Bind data to the ListView
        lstview.setAdapter(adapter);
        //getWorkoutNameAndDate();
    }

    public void getWorkoutIDS(){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String ids = dbHandler.getAllWorkoutIds();
        String[] splitted = ids.split(" ");

        LinkedHashSet<String> array = new LinkedHashSet<String>(Arrays.asList(splitted));
        String[] noDuplicatesID = array.toArray(new String[array.size()]);
        int size = noDuplicatesID.length;
        String result = "";
        for(int i = 0; i < noDuplicatesID.length; i++) {
            result += noDuplicatesID[i] + " ";
        }
        text.setText(result);

    }



    public void clickMe(View view){
        Button bt=(Button)view;
        Toast.makeText(this, "Button "+bt.getText().toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
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
        }

        return super.onOptionsItemSelected(item);
    }
}
