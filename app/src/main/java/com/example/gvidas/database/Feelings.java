package com.example.gvidas.database;

import android.icu.util.LocaleData;

import java.time.LocalDate;
import java.util.Date;

public class Feelings {
    public int workoutID;
    public int ID;
    public int tirednessLevel;
    public int energyLevel;
    public String comment;
    public String workoutDate;

    public Feelings(int workoutID, int ID, int tirednessLevel, int energyLevel, String comment, String workoutDate) {
        this.workoutID = workoutID;
        this.ID = ID;
        this.tirednessLevel = tirednessLevel;
        this.energyLevel = energyLevel;
        this.comment = comment;
        this.workoutDate = workoutDate;
    }

    public String getComment() {
        return comment;
    }

    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

    public int getTirednessLevel() {
        return tirednessLevel;
    }

    public int getID() {
        return ID;
    }

    public void setTirednessLevel(int tirednessLevel) {
        this.tirednessLevel = tirednessLevel;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    public String getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }
}
