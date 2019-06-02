package com.example.gvidas.database;

import java.util.Date;

public class WorkoutDone {
    public int ID;
    public int randomID;
    public String WorkoutDoneName;
    public String exerciseName;
    public int weight;

    public WorkoutDone() {
    }

    public WorkoutDone(int ID, int randomID, String WorkoutDoneName, String exerciseName, int weight) {
        this.ID = ID;
        this.randomID = randomID;
        this.WorkoutDoneName = WorkoutDoneName;
        this.exerciseName = exerciseName;
        this.weight = weight;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getWorkoutDoneName() {
        return WorkoutDoneName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRandomID() {
        return randomID;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
