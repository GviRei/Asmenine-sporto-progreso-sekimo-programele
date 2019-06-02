package com.example.gvidas.database;

public class ExerciseInPlan {
    public String exerciseName;
    public int sets;
    public int reps;


    public ExerciseInPlan(String name, int sets, int reps) {
        this.exerciseName = name;
        this.sets = sets;
        this.reps = reps;
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
}
