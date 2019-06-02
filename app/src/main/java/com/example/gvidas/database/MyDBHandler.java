package com.example.gvidas.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;


public class MyDBHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sportDB.db";

    //Exercise table
    public static final String TABLE_EXERCISE = "Exercise";
    public static final String EXERCISE_ID = "ExerciseID";
    public static final String EXERCISE_NAME = "ExerciseName";
    public static final String EXERCISE_CATEGORY = "ExerciseCategory";

    //Workout plan table
    public static final String TABLE_WORKOUT = "Workout";
    public static final String WORKOUT_ID = "WorkoutID";
    public static final String WORKOUT_NAME = "WorkoutName";
    public static final String WORKOUT_EXERCISE = "WorkoutExercise";

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
    public static final String WORKOUTDONE_NAME = "WorkoutDoneName";
    public static final String WORKOUTDONE_EXERCISENAME = "ExerciseName";
    public static final String WORKOUTDONE_WEIGHT = "Weight";

    public static final String TABLE_FEELINGS = "Feelings";
    public static final String FEELINGS_WORKOUTID = "WorkoutID";
    public static final String FEELINGS_ID = "matchID";
    public static final String FEELINGS_TIREDNESS = "Tiredness";
    public static final String FEELINGS_ENERGY = "Energy";
    public static final String FEELINGS_COMMENT = "Comment";
    public static final String FEELINGS_DATE = "Date";

    public static final String TABLE_VO2MAX = "Vo2Max";
    public static final String VO2MAX_ID = "Vo2maxID";
    public static final String VO2MAX_rateMax = "rateMax";
    public static final String VO2MAX_rateRest = "rateRest";
    public static final String VO2MAX_vo2max = "vo2max";
    public static final String VO2MAX_DATE = "dateOfCalculations";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public static final String CREATE_TABLE_VO2MAX =
            "CREATE TABLE " + TABLE_VO2MAX + "(" +
                    VO2MAX_ID + " INTEGER PRIMARY KEY," +
                    VO2MAX_rateMax + " DOUBLE," +
                    VO2MAX_rateRest + " DOUBLE," +
                    VO2MAX_vo2max + " DOUBLE," +
                    VO2MAX_DATE + " TEXT" + " )";


    public static final String CREATE_TABLE_WORKOUTDONE =
            "CREATE TABLE " + TABLE_WORKOUTDONE + "(" + WORKOUTDONE_ID +
                    " INTEGER PRIMARY KEY," + WORKOUTDONE_RANDID + " INTEGER," +
                    WORKOUTDONE_NAME + " TEXT," +
                    WORKOUTDONE_EXERCISENAME + " TEXT," +
                    WORKOUTDONE_WEIGHT + " INTEGER" + " )";


    private static final String CREATE_TABLE_EXERCISE =
            "CREATE TABLE " + TABLE_EXERCISE + "(" + EXERCISE_ID +
                    " INTEGER PRIMARY KEY," + EXERCISE_NAME + " TEXT," + EXERCISE_CATEGORY + " TEXT )";

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

    private static final String CREATE_TABLE_FEELINGS =
            "CREATE TABLE " + TABLE_FEELINGS + "(" +
                    FEELINGS_WORKOUTID + " INTEGER," +
                    FEELINGS_ID + " INTEGER," +
                    FEELINGS_TIREDNESS + " INTEGER," +
                    FEELINGS_ENERGY + " INTEGER," +
                    FEELINGS_COMMENT + " TEXT," +
                    FEELINGS_DATE + " TEXT," +

                    " FOREIGN KEY (" + FEELINGS_WORKOUTID + ") " +
                    "REFERENCES " + TABLE_WORKOUTDONE + " (" + WORKOUTDONE_ID + ") )";


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
        db.execSQL(CREATE_TABLE_FEELINGS);
        db.execSQL(CREATE_TABLE_VO2MAX);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addVO2Max(Vo2Max max) {
        ContentValues values = new ContentValues();
        //  values.put(VO2MAX_ID, max.getVo2maxID());
        values.put(VO2MAX_rateMax, max.getRateMax());
        values.put(VO2MAX_rateRest, max.getRateRest());
        values.put(VO2MAX_vo2max, max.getVo2max());
        values.put(VO2MAX_DATE, max.getDateOfCalculations());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VO2MAX, null, values);
        db.close();
    }

    public String loadVo2MaxResult() {
        String result = "";
        String query = "Select * FROM " + TABLE_VO2MAX;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Double result_3 = cursor.getDouble(3);
            String result_4 = cursor.getString(4);

            result += result_3 + " ";
        }
        cursor.close();
        db.close();
        return result;
    }

    public String loadVo2MaxDate() {
        String result = "";
        String query = "Select * FROM " + TABLE_VO2MAX;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Double result_3 = cursor.getDouble(3);
            String result_4 = cursor.getString(4);

            result += result_4 + " ";
        }
        cursor.close();
        db.close();
        return result;
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

    public String loadVo2Max() {
        String result = "";
        String query = "Select * FROM " + TABLE_VO2MAX;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            Double result_1 = cursor.getDouble(1);
            Double result_2 = cursor.getDouble(2);
            Double result_3 = cursor.getDouble(3);
            String result_4 = cursor.getString(4);

            result += result_0 + " " + result_1 + " " + result_2 + " " + result_3 + " " + result_4 + System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }



    public String getWorkoutIDString(int id) {
        String wid = "";
        String query = "SELECT * FROM " + TABLE_WORKOUTDONE + " WHERE " + WORKOUTDONE_RANDID + " = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)}, null);
        while (cursor.moveToNext()) {
            int getId = cursor.getInt(0);
            wid += String.valueOf(getId) + " ";
        }

        cursor.close();
        db.close();
        return wid;
    }

    //Add record to database
    public void addExercise(Exercise exercise) {

        ContentValues values = new ContentValues();
        values.put(EXERCISE_ID, exercise.getExerciseID());
        values.put(EXERCISE_NAME, exercise.getExerciseName());
        values.put(EXERCISE_CATEGORY, exercise.getExerciseCategory());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EXERCISE, null, values);
        db.close();
    }

    public void saveFinishedWorkout(WorkoutDone workout) {
        ContentValues values = new ContentValues();
        // values.put(WORKOUTDONE_ID, workout.getID());
        values.put(WORKOUTDONE_RANDID, workout.getRandomID());
        values.put(WORKOUTDONE_NAME, workout.getWorkoutDoneName());
        values.put(WORKOUTDONE_EXERCISENAME, workout.getExerciseName());
        values.put(WORKOUTDONE_WEIGHT, workout.getWeight());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_WORKOUTDONE, null, values);
        db.close();
    }

    public void addFeelingsToDatabase(Feelings feelings) {
        ContentValues values = new ContentValues();
        values.put(FEELINGS_WORKOUTID, feelings.getWorkoutID());
        values.put(FEELINGS_ID, feelings.getID());
        values.put(FEELINGS_TIREDNESS, feelings.getTirednessLevel());
        values.put(FEELINGS_ENERGY, feelings.getEnergyLevel());
        values.put(FEELINGS_COMMENT, feelings.getComment());
        values.put(FEELINGS_DATE, feelings.getWorkoutDate());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_FEELINGS, null, values);
        db.close();
    }

    public String loadWorkout(int workoutID) {
        String result = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT*FROM " + TABLE_WORKOUTDONE + " WHERE " + WORKOUTDONE_RANDID + " = ?", new String[]{String.valueOf(workoutID)}, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            int result_1 = cursor.getInt(1);
            String result_2 = cursor.getString(2);
            int result_3 = cursor.getInt(3);

            result += String.valueOf(result_0) + " " + String.valueOf(result_1) + " " + result_2 + " " + String.valueOf(result_3) + System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public String getAllWorkoutIds() {
        String result = "";
        String query = "SELECT*FROM " + TABLE_WORKOUTDONE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_id = cursor.getInt(1);
            ;
            result += String.valueOf(result_id) + " ";
        }
        cursor.close();

        db.close();
        return result;

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


    //get exercise id from selected spinner item
    public int getExerciseId(String name) {
        int id = 0;
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
   /* public String[] loadExercise() {
        String result = "";
        String query = "SELECT*FROM " + TABLE_EXERCISE;
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> spinnerContent = new ArrayList<String>();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String result_1 = cursor.getString(1);
            spinnerContent.add(result_1);
            result += String.valueOf(result_1);
        }
        cursor.close();
        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);
        db.close();
        return allSpinner;
    }*/

       public String[] loadExercise(String category) {
        String result = "";
        String query = "SELECT*FROM " + TABLE_EXERCISE + " WHERE " + EXERCISE_CATEGORY + " = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> spinnerContent = new ArrayList<String>();
        Cursor cursor = db.rawQuery(query, new String[]{category}, null);
        while (cursor.moveToNext()) {
            String result_1 = cursor.getString(1);
            spinnerContent.add(result_1);
            result += String.valueOf(result_1);
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
            String result_1 = cursor.getString(1);
            spinnerContent.add(result_1);
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
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);
            result += String.valueOf(result_1) + " " + String.valueOf(result_2) + "x" + String.valueOf(result_3) + System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public String loadAllDataFromWorkout(int id) {
        String result = "";
        String query = "SELECT w.ExerciseName, w.Weight, f.Comment FROM " +
                TABLE_WORKOUTDONE +
                " AS w " +
                " JOIN " +
                TABLE_FEELINGS +
                " AS f " + " ON w.WorkoutDoneID = f.WorkoutID" +
                " WHERE ? = f.matchID";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)}, null);
        while (cursor.moveToNext()) {
            String result_0 = cursor.getString(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + String.valueOf(result_1)
                    + System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public String loadWorkoutTitle(int id) {
        String result = "";
        String query = "SELECT w.WorkoutDoneName FROM " +
                TABLE_WORKOUTDONE +
                " AS w " +
                " JOIN " +
                TABLE_FEELINGS +
                " AS f " + " ON w.WorkoutDoneID = f.WorkoutID" +
                " WHERE ? = f.matchID";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)}, null);
        while (cursor.moveToNext()) {
            String result_0 = cursor.getString(0);
            result = String.valueOf(result_0);
        }
        cursor.close();
        db.close();
        return result;
    }

    public String loadWorkoutComment(int id) {
        String result = "";
        String query = "SELECT f.Comment FROM " +
                TABLE_WORKOUTDONE +
                " AS w " +
                " JOIN " +
                TABLE_FEELINGS +
                " AS f " + " ON w.WorkoutDoneID = f.WorkoutID" +
                " WHERE ? = f.matchID";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)}, null);
        while (cursor.moveToNext()) {
            String result_0 = cursor.getString(0);
            result = String.valueOf(result_0);

        }
        cursor.close();
        db.close();
        return result;
    }

    public String loadWorkoutDataForListView(int id) {
        String result = "";
        String query = "SELECT w.WorkoutDoneName, f.Date FROM " +
                TABLE_WORKOUTDONE +
                " AS w " +
                " JOIN " +
                TABLE_FEELINGS +
                " AS f " + " ON w.WorkoutDoneID = f.WorkoutID" +
                " WHERE ? = f.matchID";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)}, null);
        while (cursor.moveToNext()) {
            String result_0 = cursor.getString(0);
            String result_1 = cursor.getString(1);
            result = String.valueOf(result_0) + " " + String.valueOf(result_1);
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
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_1) + ",";
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
    public void deleteWorkout(int ID) {
        boolean result = false;
        String query = "SELECT*FROM " + TABLE_WORKOUTDONE + " AS w " + "JOIN " + TABLE_FEELINGS + " AS f" + " ON w.WorkoutDoneRandID = f.matchID" + " WHERE " + WORKOUTDONE_RANDID + " = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(ID)}, null);
        Exercise exercise = new Exercise();
        if (cursor.moveToFirst()) {
            //exercise.setExerciseID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_FEELINGS, FEELINGS_ID + " = ?", new String[]{String.valueOf(ID)});
            db.delete(TABLE_WORKOUTDONE, WORKOUTDONE_RANDID + " = ?",
                    new String[]{
                            String.valueOf(ID)
                    });

            cursor.close();
            result = true;
        }
        db.close();
        //return result;
    }


    // public Exercise findHandler(String exerciseName){}
    //Delete record from database
  /*  public void deleteWorkout(String name, String date) {
        boolean result = false;
        String query = "SELECT*FROM " + TABLE_WORKOUTDONE + " AS w " + "JOIN " + TABLE_FEELINGS + " AS f" + " ON w.WorkoutDoneRandID = f.matchID" + " WHERE w.WorkoutDoneName " + " = ?" +
               " AND f.Date = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{name, date}, null);
        Exercise exercise = new Exercise();
        if (cursor.moveToFirst()) {
            //exercise.setExerciseID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_FEELINGS, FEELINGS_DATE+ " = ?", new String[]{date});
            db.delete(TABLE_WORKOUTDONE, WORKOUTDONE_RANDID + " = ?",
                    new String[]{
                            name
                    });

            cursor.close();
            result = true;
        }
        db.close();
        //return result;
    }*/

    public int getProfileID() {
        int result = 0;
        String selectQuery = "SELECT ProfileID FROM " + TABLE_PROFILE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            int result_1 = cursor.getInt(0);
            result = result_1;
        }
        cursor.close();
        db.close();
        return result;
    }


    public void updateProfile(int ID, String name, int age, int height, int weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(PROFILE_NAME, name);
        args.put(PROFILE_AGE, age);
        args.put(PROFILE_HEIGHT, height);
        args.put(PROFILE_WEIGHT, weight);
        db.update(TABLE_PROFILE, args, PROFILE_ID + " = " + ID, null);
        db.close();
    }


}
