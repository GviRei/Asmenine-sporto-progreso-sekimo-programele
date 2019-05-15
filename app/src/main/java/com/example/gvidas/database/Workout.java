package com.example.gvidas.database;

import java.util.ArrayList;
import java.util.Date;

public class Workout {
    public int workoutID;
    public String workoutName;
    //  public Date date;

    public Workout() {
    }

    public Workout(int id, String name) {
        this.workoutID = id;
        this.workoutName = name;
        // this.date = date;
    }

    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

   /* public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }*/

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }


}
