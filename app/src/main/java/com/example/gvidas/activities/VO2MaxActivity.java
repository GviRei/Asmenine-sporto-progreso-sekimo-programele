package com.example.gvidas.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.database.Vo2Max;
import com.example.gvidas.sportapplication.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class VO2MaxActivity extends AppCompatActivity {

    TextView timer, infoText;
    TextView loadData;
    EditText heartRate;
    Button startTimer, save;
    int time = 6;
    double rateMax = 0;
    double rateRest = 0;
    double vo2Max  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vo2_max);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        timer = (TextView) findViewById(R.id.vo2mamxTimer);
        heartRate = (EditText) findViewById(R.id.vo2maxTextView);
        startTimer = (Button) findViewById(R.id.startTimer);
        save = (Button)findViewById(R.id.saveVo2Max) ;
        infoText = (TextView)findViewById(R.id.vo2maxInfoText);
        loadData = (TextView)findViewById(R.id.testLoad);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(heartRate.getText().toString().trim().length() == 0) {
                    Toast.makeText(VO2MaxActivity.this, "Please enter heart rate", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    double max = calculateRateMax();
                    double maxRounded = Math.round(max * 100D) / 100D;
                    double vo2max = calculateVo2Max();
                    double vo2maxRounded = Math.round(vo2max * 100D) / 100D;

                    double rest = Double.parseDouble(heartRate.getText().toString()) * 10;
                    SpannableStringBuilder str = new SpannableStringBuilder("Your max heart rate is: " + String.valueOf(maxRounded) + "\n"
                            + "Your resting heart rate is: " + String.valueOf(rest) + "\n"
                            + "Your Vo2Max is " + String.valueOf(vo2maxRounded) + " mL/kg/min " + "\n");
                    str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 24, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 58, 65, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 78, 85, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    infoText.setText(str);
                    saveHeartRate(rest, maxRounded, vo2maxRounded);
                  //  load();
                }
            }
        });


    }

    public void load(){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String data = dbHandler.loadVo2Max();
        loadData.setText(data);
    }


    public double calculateRateMax(){
        //double rateMax = 0;
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String str = dbHandler.loadProfileData();
        String[] splitted = str.split(" ");
        int age = Integer.parseInt(splitted[1]);
        return rateMax = 205.8 - (0.685 * age);
        //return rateMax;
    }
    public double calculateVo2Max() {
        rateRest = Double.parseDouble(heartRate.getText().toString()) * 10;
        return vo2Max = 15 * (rateMax/rateRest);
    }

    public void saveHeartRate(double rest, double max, double vo2){
        Random rand = new Random();
        int id = rand.nextInt(1000+1);
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = format.format(today);
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Vo2Max maxx = new Vo2Max(id, max, rest, vo2, todayDate);
        dbHandler.addVO2Max(maxx);
    }


    public void startTimer() {
        new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText("0:" + checkDigit(time));
                time--;
            }

            @Override
            public void onFinish() {
                timer.setText("Timer");
            }
        }.start();
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
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


}
