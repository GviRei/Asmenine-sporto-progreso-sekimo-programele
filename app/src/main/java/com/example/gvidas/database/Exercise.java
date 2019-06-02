package com.example.gvidas.database;

public class Exercise {
    public int exerciseID;
    public String exerciseName;
    public String exerciseCategory;

    public Exercise() {
    }

    public Exercise(int id, String name, String category) {
        this.exerciseID = id;
        this.exerciseName = name;
        this.exerciseCategory = category;
    }


    public int getExerciseID() {
        return exerciseID;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getExerciseCategory() {
        return exerciseCategory;
    }
}
