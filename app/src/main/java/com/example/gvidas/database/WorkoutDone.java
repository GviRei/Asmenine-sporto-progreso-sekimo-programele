package com.example.gvidas.database;

import java.util.Date;

public class WorkoutDone {
    public int ID;
    public int randomID;
    public int weight;
    public int tirednessLevel;
    public int energyLevel;
    public Date workoutDate;

    public WorkoutDone() {}

    public WorkoutDone(int ID, int randomID, int weight, int tirednessLevel, int energyLevel, Date workoutDate) {
        this.ID = ID;
        this.randomID = randomID;
        this.weight = weight;
        this.tirednessLevel = tirednessLevel;
        this.energyLevel = energyLevel;
        this.workoutDate = workoutDate;
    }

    public Date getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(Date workoutDate) {
        this.workoutDate = workoutDate;
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

    public int getTirednessLevel() {
        return tirednessLevel;
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
}
