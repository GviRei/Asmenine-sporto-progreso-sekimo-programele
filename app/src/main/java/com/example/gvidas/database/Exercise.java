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

    public void setExerciseID(int exerciseID) {
        this.exerciseID = exerciseID;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseCategory() {
        return exerciseCategory;
    }

    public void setExerciseCategory(String exerciseCategory) {
        this.exerciseCategory = exerciseCategory;
    }
}
