package com.example.gvidas.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class MyDBHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sportDB.db";

    //Exercise table
    public static final String TABLE_EXERCISE = "Exercise";
    public static final String EXERCISE_ID = "ExerciseID";
    public static final String EXERCISE_NAME = "ExerciseName";
    public static final String EXERCISE_CATEGORY = "ExerciseCategory";
    public static final String EXERCISE_SETS = "ExerciseSets";
    public static final String EXERCISE_REPS = "ExerciseReps";

    //Workout plan table
    public static final String TABLE_WORKOUT = "Workout";
    public static final String WORKOUT_ID = "WorkoutID";
    public static final String WORKOUT_NAME = "WorkoutName";
    public static final String WORKOUT_EXERCISE = "WorkoutExercise";
    // public static final String WOKKOUT_DATE = "WorkoutDate";

    //Profile table
    public static final String TABLE_PROFILE = "Profile";
    public static final String PROFILE_ID = "ProfileID";
    public static final String PROFILE_NAME = "Name";
    public static final String PROFILE_AGE = "Age";
    public static final String PROFILE_HEIGHT = "Height";
    public static final String PROFILE_WEIGHT = "Weight";

    //Relationship between exercise and workout table
    public static final String TABLE_TRAININGEXERCISE = "Training_Exercise";
    public static final String TID = "TID";
    public static final String TEXERCISE_LINK = "TExerciseID";
    public static final String TWORKOUT_LINK = "TWorkoutID";
    public static final String TEXERCISE_SETS = "TExerciseSets";
    public static final String TEXERCISE_REPS = "TExerciseReps";
    public static final String TEXERCISE_WEIGHT = "TExerciseWeight";

    //Finished workout data table
    public static final String TABLE_WORKOUTDONE = "WorkoutDone";
    public static final String WORKOUTDONE_ID = "WorkoutDoneID";
    public static final String WORKOUTDONE_RANDID = "WorkoutDoneRandID"; //ID which is used to group all rows for one workout
    public static final String WORKOUTDONE_TIREDNESS = "TirednessLevel";
    public static final String WORKOUTDONE_ENERGY = "EnergyLevel";

    //Relationship table between exercises and workoutdone tables
    public static final String TABLE_WEXERCISEDONE = "Exercise_WorkoutDone";
    public static final String WEXERCISE_LINK = "WExerciseID";
    public static final String WWORKOUTDONE_LINK = "WWorkoutDoneID";




    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public static final String CREATE_TABLE_WORKOUTDONE =
            "CREATE TABLE " + TABLE_WORKOUTDONE + "(" + WORKOUTDONE_ID +
                    " INTEGER PRIMARY KEY," + WORKOUTDONE_RANDID + " INTEGER," +
                    WORKOUTDONE_TIREDNESS + " INTEGER," +
                    WORKOUTDONE_ENERGY + " INTEGER" + ")";





    private static final String CREATE_TABLE_EXERCISE =
            "CREATE TABLE " + TABLE_EXERCISE + "(" + EXERCISE_ID +
                    " INTEGER PRIMARY KEY," + EXERCISE_NAME + " TEXT )";

    private static final String CREATE_TABLE_PROFILE =
            "CREATE TABLE " + TABLE_PROFILE + "(" + PROFILE_ID +
                    " INTEGER PRIMARY KEY," + PROFILE_NAME + " TEXT," + PROFILE_AGE + " INTEGER," + PROFILE_HEIGHT +
                    " INTEGER," + PROFILE_WEIGHT + " INTEGER" + ")";

    private static final String CREATE_TABLE_WORKOUT =
            "CREATE TABLE " + TABLE_WORKOUT + "(" + WORKOUT_ID +
                    " INTEGER PRIMARY KEY," + WORKOUT_NAME + " TEXT )";

    private static final String CREATE_TABLE_TRAININGEXERCISE =
            "CREATE TABLE " + TABLE_TRAININGEXERCISE + "(" +
                    TWORKOUT_LINK + " INTEGER," +
                    TEXERCISE_LINK + " INTEGER," +
                    TEXERCISE_SETS + " INTEGER," +
                    TEXERCISE_REPS + " INTEGER," +
                    TEXERCISE_WEIGHT + " INTEGER," +

                    "FOREIGN KEY (" + TWORKOUT_LINK + ") " +
                    "REFERENCES " + TABLE_WORKOUT + " (" + WORKOUT_ID + ")," +

                    " FOREIGN KEY (" + TEXERCISE_LINK + ") " +
                    "REFERENCES " + TABLE_EXERCISE + " (" + EXERCISE_ID + ") )";

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EXERCISE);
        db.execSQL(CREATE_TABLE_PROFILE);
        db.execSQL(CREATE_TABLE_WORKOUT);
        db.execSQL(CREATE_TABLE_TRAININGEXERCISE);
        db.execSQL(CREATE_TABLE_WORKOUTDONE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Add record to database
    public void addWorkout(Workout workout) {

        ContentValues values = new ContentValues();
        values.put(WORKOUT_ID, workout.getWorkoutID());
        values.put(WORKOUT_NAME, workout.getWorkoutName());
        //values.put(EXERCISE_CATEGORY, exercise.getExerciseCategory());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_WORKOUT, null, values);
        db.close();
    }

    //add exercises, sets and reps to relationship table
    public void addExerciseToWorkout(TrainingExercise exercise) {
        ContentValues values = new ContentValues();
        values.put(TWORKOUT_LINK, exercise.getTWorkoutID());
        values.put(TEXERCISE_LINK, exercise.getTExerciseID());
        values.put(TEXERCISE_SETS, exercise.getSets());
        values.put(TEXERCISE_REPS, exercise.getReps());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TRAININGEXERCISE, null, values);
        db.close();
    }

    public String loadWorkout() {
        String result = "";
        String query = "SELECT*FROM " + TABLE_WORKOUT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);

            result += String.valueOf(result_0) + " " + result_1 + " " + System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    //Add record to database
    public void addExercise(Exercise exercise) {

        ContentValues values = new ContentValues();
        values.put(EXERCISE_ID, exercise.getExerciseID());
        values.put(EXERCISE_NAME, exercise.getExerciseName());
        //values.put(EXERCISE_CATEGORY, exercise.getExerciseCategory());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EXERCISE, null, values);
        db.close();
    }

    //Use this when adding exercise to workout plan
    public long addExerciseToArray(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EXERCISE_NAME, name);
        return db.insert(TABLE_EXERCISE, null, cv);
    }


    public long addWorkout(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(WORKOUT_NAME, name);
        return db.insert(TABLE_WORKOUT, null, cv);
    }

    //Add profile data to database
    public void addProfile(Profile profile) {
        ContentValues values = new ContentValues();
        values.put(PROFILE_ID, profile.getProfileID());
        values.put(PROFILE_NAME, profile.getName());
        values.put(PROFILE_AGE, profile.getAge());
        values.put(PROFILE_HEIGHT, profile.getHeight());
        values.put(PROFILE_WEIGHT, profile.getWeight());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PROFILE, null, values);
        db.close();
    }

    public int getWorkoutId() {
        int id = 0;
        String query = "SELECT*FROM " + TABLE_TRAININGEXERCISE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    id = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return id;
    }


    //get exercise id from selected spinner item
    public int getExerciseId(String name) {
        int id = 0;
        //String query = "SELECT*FROM " + TABLE_EXERCISE " WHERE " + EXERCISE_NAME + " = " + name;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT*FROM Exercise WHERE ExerciseName = ?", new String[]{name}, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    id = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return id;
    }

    public int getWorkoutPlanId(String name) {
        int id = 0;
        //String query = "SELECT*FROM " + TABLE_EXERCISE " WHERE " + EXERCISE_NAME + " = " + name;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT*FROM Workout WHERE WorkoutName = ?", new String[]{name}, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    id = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return id;
    }

    //Load data from database
    public String loadExercise2() {
        String result = "";
        String query = "SELECT*FROM " + TABLE_EXERCISE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            //result += String.valueOf(result_0) + " " + result_1 + System.getProperty("line.separator");
            result += String.valueOf(result_0);
        }
        cursor.close();
        db.close();
        return result;
    }

    //Load data from database
    public String[] loadExercise() {
        String result = "";
        String query = "SELECT*FROM " + TABLE_EXERCISE;
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> spinnerContent = new ArrayList<String>();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            spinnerContent.add(result_1);
            //result += String.valueOf(result_0) + " " + result_1 + System.getProperty("line.separator");
            result += String.valueOf(result_0);
        }

        cursor.close();
        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);
        db.close();

        return allSpinner;
    }

    //Load workout data from database
    public String[] loadWorkoutPlan2() {
        String result = "";
        String query = "SELECT*FROM " + TABLE_WORKOUT;
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> spinnerContent = new ArrayList<String>();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            spinnerContent.add(result_1);
            //result += String.valueOf(result_0) + " " + result_1 + System.getProperty("line.separator");
            result += String.valueOf(result_1);
        }

        cursor.close();
        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);
        db.close();

        return allSpinner;
    }

    public String loadProfile() {
        String result = "";
        String selectQuery = "SELECT * FROM " + TABLE_PROFILE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            int result_2 = cursor.getInt(2);
            result += String.valueOf(result_0) + " " + result_1 + " " + String.valueOf(result_2) + " " + System.getProperty("line.separator");
        }

        cursor.close();
        db.close();
        return result;
    }

    public String loadWorkoutPlan(int id) {
        String result = "";
        String query = "SELECT w.WorkoutName, e.ExerciseName, te.TExerciseSets, te.TExerciseReps FROM "
                + TABLE_EXERCISE
                + " AS e " +
                " JOIN " +
                TABLE_TRAININGEXERCISE +
                " AS te" + " ON e.ExerciseID = te.TExerciseID" +
                " JOIN " +
                TABLE_WORKOUT +
                " AS w" + " ON w.WorkoutID = te.TWorkoutID" +
                " WHERE ? = te.TWorkoutID";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)}, null);
        while (cursor.moveToNext()) {
            String result_0 = cursor.getString(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);
            result += String.valueOf(result_0) + " " + String.valueOf(result_1) + " " + String.valueOf(result_2) + "x" + String.valueOf(result_3) + System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public String loadWorkoutPlanOnlyExercises(int id) {
        String result = "";
        String query = "SELECT w.WorkoutName, e.ExerciseName, te.TExerciseSets, te.TExerciseReps FROM "
                + TABLE_EXERCISE
                + " AS e " +
                " JOIN " +
                TABLE_TRAININGEXERCISE +
                " AS te" + " ON e.ExerciseID = te.TExerciseID" +
                " JOIN " +
                TABLE_WORKOUT +
                " AS w" + " ON w.WorkoutID = te.TWorkoutID" +
                " WHERE ? = te.TWorkoutID";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)}, null);
        while (cursor.moveToNext()) {
            String result_0 = cursor.getString(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);
            result +=  String.valueOf(result_1) + " ";
        }
        cursor.close();
        db.close();
        return result;
    }

    public String loadProfileData() {
        String result = "";
        String selectQuery = "SELECT * FROM " + TABLE_PROFILE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);
            String result_4 = cursor.getString(4);
            result = String.valueOf(result_1) + " " + String.valueOf(result_2) + " " + String.valueOf(result_3) + " " + String.valueOf(result_4);
        }
        cursor.close();
        db.close();
        return result;
    }


    // public Exercise findHandler(String exerciseName){}
    //Delete record from database
    public boolean deleteHandler(int ID) {
        boolean result = false;
        String query = "Select*FROM " + TABLE_EXERCISE + "WHERE " + EXERCISE_ID + "= '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Exercise exercise = new Exercise();
        if (cursor.moveToFirst()) {
            exercise.setExerciseID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_EXERCISE, EXERCISE_ID + "=?",
                    new String[]{
                            String.valueOf(exercise.getExerciseID())
                    });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateHandler(int ID, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(EXERCISE_ID, ID);
        args.put(EXERCISE_NAME, name);
        return db.update(TABLE_EXERCISE, args, EXERCISE_ID + "=" + ID, null) > 0;
    }

    public List<String> getAllLabels() {
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  EXERCISE_NAME FROM " + TABLE_EXERCISE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }
}