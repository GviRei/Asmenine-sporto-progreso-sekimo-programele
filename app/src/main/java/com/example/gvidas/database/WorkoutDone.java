package com.example.gvidas.database;

import java.util.Date;

public class WorkoutDone {
    public int ID;
    public int randomID;
    public String exerciseName;
    public int weight;

    public WorkoutDone() {}

    public WorkoutDone(int ID, int randomID,String exerciseName, int weight) {
        this.ID = ID;
        this.randomID = randomID;
        this.exerciseName = exerciseName;
        this.weight = weight;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
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

    public void setRandomID(int randomID) {
        this.randomID = randomID;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
