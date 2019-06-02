package com.example.gvidas.database;

public class TrainingExercise {
    public int TWorkoutID;
    public int TExerciseID;
    public int sets;
    public int reps;


    public TrainingExercise(int wID, int eID, int sets, int reps) {
        this.TWorkoutID = wID;
        this.TExerciseID = eID;
        this.sets = sets;
        this.reps = reps;
    }

    public int getTWorkoutID() {
        return TWorkoutID;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getTExerciseID() {
        return TExerciseID;
    }

}