package com.example.gvidas.activities.Progress;

import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AdvancedVo2MaxActivity extends AppCompatActivity implements SensorEventListener {
    Button saveAdvanced, rate, timer;
    EditText advancedRate, advancedMinutes;
    TextView infoText, timerView, infoText2;
    double rateMax = 0;
    double rateRest = 0;
    double vo2Max  = 0;
    int time = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_vo2_max);
        saveAdvanced = (Button)findViewById(R.id.saveAdvancedVo2Max);
        advancedRate = (EditText)findViewById(R.id.advancedRate);
        advancedMinutes = (EditText) findViewById(R.id.advancedMinutes);
        infoText = (TextView)findViewById(R.id.testAdvancedLoad);
        infoText2 = (TextView)findViewById(R.id.testAdvancedLoad2);
        timerView = (TextView) findViewById(R.id.timer);
        rate = (Button) findViewById(R.id.rateMonitor);
        timer = (Button) findViewById(R.id.timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Rock Port Walking Fitness Test");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Calculating Vo2Max");
        builder.setMessage(Html.fromHtml("It is Rock Port Walking Fitness Test" +
                "<br>" +
                " walk 1,6 km in fast pace and then count your heart rate. You can use your phone heart monitor or simply " +
                "press 'timer' and count your rate with fingers"));
        AlertDialog dialog = builder.create();

        dialog.show();

        saveAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(advancedRate.getText().toString().trim().length() == 0 || advancedMinutes.getText().toString().trim().length() == 0) {
                    Toast.makeText(AdvancedVo2MaxActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    double max = calculateRateMax();
                    double maxRounded = Math.round(max * 100D) / 100D;
                    double vo2max = calculateAdvancedVo2Max();
                    double vo2maxRounded = Math.round(vo2max * 100D) / 100D;
                    double rest = 0;
                    if(Double.parseDouble(advancedRate.getText().toString()) > 40){
                        rest = Double.parseDouble(advancedRate.getText().toString());
                    } else {
                        rest = Double.parseDouble(advancedRate.getText().toString()) * 6;
                    }
                    // double rest = Double.parseDouble(advancedRate.getText().toString()) * 6;
                    SpannableStringBuilder str = new SpannableStringBuilder("Your max heart rate is: " + String.valueOf(maxRounded) + "\n"
                            + "Your resting heart rate is: " + String.valueOf(rest) + "\n"
                            + "Your Vo2Max is " + String.valueOf(vo2maxRounded) + " mL/kg/min " + "\n");
                    str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 24, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 58, 64, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 78, 85, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    infoText.setText(str);
                    saveHeartRate(rest, maxRounded, vo2maxRounded);
                }
            }
        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartRateMonitor();
            }
        });
    }

    public void saveHeartRate(double rest, double max, double vo2){
        Random rand = new Random();
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = format.format(today);
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Vo2Max maxx = new Vo2Max(max, rest, vo2, todayDate);
        dbHandler.addVO2Max(maxx);
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

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    public double calculateRateMax(){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String str = dbHandler.loadProfileData();
        String[] splitted = str.split(" ");
        int age = Integer.parseInt(splitted[1]);

        return rateMax = 205.8 - (0.769 * age);
    }

    public double calculateAdvancedVo2Max() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String str = dbHandler.loadProfileData();
        String[] splitted = str.split(" ");
        String gender = splitted[2];
        int walkTime = Integer.parseInt(advancedMinutes.getText().toString());
        int rate = 0;
        if(Integer.parseInt(advancedRate.getText().toString()) > 40){
            rate = Integer.parseInt(advancedRate.getText().toString());
        } else {
            rate = Integer.parseInt(advancedRate.getText().toString()) * 6;
        }
        //int rate = Integer.parseInt(advancedRate.getText().toString()) * 6;
        int number = 0;
        if(gender.equals("Male") ){
            number = 1;
        }
        int weight = Integer.parseInt((splitted[4]));
        int age = Integer.parseInt(splitted[1]);
        vo2Max = 132.853 - (0.076 * (weight * 2.2046)) - (0.3877 * age) + (6.315 * number) - (3.2649 * walkTime) - (0.156 * rate);

        return vo2Max;
    }

    public void startTimer() {
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerView.setText("0:" + checkDigit(time));
                time--;
            }

            @Override
            public void onFinish() {
                timerView.setText("Timer");
            }
        }.start();
    }


    public void heartRateMonitor() {
        SensorManager sMgr;
        sMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);

        Sensor sensor = null;
        sensor = sMgr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if(sensor != null)
            infoText2.setText("load sensor");
        else
            infoText2.setText("no load sensor");

        sMgr.registerListener(this, sensor,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            String msg = " Heart rate: " + (int)event.values[0];
            // int msg = (int)event.values[0];
            infoText2.setText(String.valueOf(msg));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
