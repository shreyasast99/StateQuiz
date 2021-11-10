package edu.uga.cs.statequiz;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * This is a SQLiteOpenHelper class, which Android uses to create, upgrade, delete an SQLite database
 * in an app.
 *
 * This class is a singleton, following the Singleton Design Pattern.
 * Only one instance of this class will exist.  To make sure, the
 * only constructor is private.
 * Access to the only instance is via the getInstance method.
 */
public class StateQuizDBHelper extends SQLiteOpenHelper {
    final String TAG = "CSVReading";
    private static final String DEBUG_TAG = "StateQuizDBHelper";

    private static final String DB_NAME = "statequizquestions.db";
    private static final int DB_VERSION = 1;

    // Define all names (strings) for table and column names.
    // This will be useful if we want to change these names later.
    public static final String TABLE_STATEQUIZ = "stateQuestions";
    public static final String STATEQUIZ_COLUMN_ID = "_id";
    public static final String STATEQUIZ_COLUMN_STATE = "state";
    public static final String STATEQUIZ_COLUMN_CAPITAL = "capital";
    public static final String STATEQUIZ_COLUMN_SECONDCITY = "secondcity";
    public static final String STATEQUIZ_COLUMN_THIRDCITY = "thirdcity";
    // public static final String STATEQUIZ_COLUMN_STATEHOOD = "statehood";
    // public static final String STATEQUIZ_COLUMN_CAPITALSINCE = "capitalsince";
    // public static final String STATEQUIZ_COLUMN_SIZERANK = "sizerank";

    // Defining all names (strings) for table and column names for past quiz table.
    public static final String TABLE_PASTSCORES = "quizResults";
    public static final String PASTSCORES_COLUMNID = "_id";
    public static final String PASTSCORES_COLUMN_SCORE = "score";
    public static final String PASTSCORES_COLUMN_DATE = "date";


    // This is a reference to the only instance for the helper.
    private static StateQuizDBHelper helperInstance;

    // A Create table SQL statement to create a table for job leads.
    // Note that _id is an auto increment primary key, i.e. the database will
    // automatically generate unique id values as keys.
    private static final String CREATE_STATEQUIZ =
            "create table " + TABLE_STATEQUIZ + " ("
                    + STATEQUIZ_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + STATEQUIZ_COLUMN_STATE + " TEXT, "
                    + STATEQUIZ_COLUMN_CAPITAL + " TEXT, "
                    + STATEQUIZ_COLUMN_SECONDCITY + " TEXT, "
                    + STATEQUIZ_COLUMN_THIRDCITY + " TEXT"
                    //+ STATEQUIZ_COLUMN_STATEHOOD+ " TEXT, "
                    //+ STATEQUIZ_COLUMN_CAPITALSINCE+ " TEXT, "
                    //+ STATEQUIZ_COLUMN_SIZERANK + " TEXT "
                    + ")";

    // Creating table SQL statement to create a table for past scores.
    private static final String CREATE_PASTSCORES =
            " create table " + TABLE_PASTSCORES + " ("
            + PASTSCORES_COLUMNID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PASTSCORES_COLUMN_DATE + " TEXT, "
            + PASTSCORES_COLUMN_SCORE + " TEXT " + ")";

    // Note that the constructor is private!
    // So, it can be called only from
    // this class, in the getInstance method.
    private StateQuizDBHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    // Access method to the single instance of the class.
    // It is synchronized, so that only one thread can executes this method.
    public static synchronized StateQuizDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new StateQuizDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    // We must override onCreate method, which will be used to create the database if
    // it does not exist yet.
    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( CREATE_STATEQUIZ );
        db.execSQL( CREATE_PASTSCORES );
        Log.d( DEBUG_TAG, "Table " + TABLE_STATEQUIZ + " created" );
        Log.d( DEBUG_TAG, "Table " + TABLE_PASTSCORES + " created" );

        //end of csv transfer
    }

    // We should override onUpgrade method, which will be used to upgrade the database if
    // its version (DB_VERSION) has changed.  This will be done automatically by Android
    // if the version will be bumped up, as we modify the database schema.
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_STATEQUIZ );
        db.execSQL( "drop table if exists " + TABLE_PASTSCORES );
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_STATEQUIZ + " upgraded" );
        Log.d( DEBUG_TAG, "Table " + TABLE_PASTSCORES + " upgraded" );
    }
}
