package edu.uga.cs.statequiz;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;


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

    private static final String DEBUG_TAG = "StateQuizDBHelper";

    private static final String DB_NAME = "statequizquestions.db";
    private static final int DB_VERSION = 1;

    // Define all names (strings) for table and column names.
    // This will be useful if we want to change these names later.
    public static final String TABLE_STATEQUIZ = "jobleads";
    public static final String STATEQUIZ_COLUMN_ID = "_id";
    public static final String STATEQUIZ_COLUMN_STATE = "state";
    public static final String STATEQUIZ_COLUMN_CAPITAL = "capital";
    public static final String STATEQUIZ_COLUMN_SECONDCITY = "secondcity";
    public static final String STATEQUIZ_COLUMN_THIRDCITY = "thirdcity";
    public static final String STATEQUIZ_COLUMN_STATEHOOD = "statehood";
    public static final String STATEQUIZ_COLUMN_CAPITALSINCE = "capitalsince";
    public static final String STATEQUIZ_COLUMN_SIZERANK = "sizerank";

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
                    + STATEQUIZ_COLUMN_THIRDCITY + " TEXT,"
                    + STATEQUIZ_COLUMN_STATEHOOD+ " TEXT, "
                    + STATEQUIZ_COLUMN_CAPITALSINCE+ " TEXT, "
                    + STATEQUIZ_COLUMN_SIZERANK + " TEXT "
                    + ")";

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

        Log.d( DEBUG_TAG, "Table " + TABLE_STATEQUIZ + " created" );
        try {
            // Open the CSV data file in the assets folder
            InputStream in_s = getAssets().open( "data.csv" );

            // get the TableLayout view
            TableLayout tableLayout = findViewById(R.id.table_main);

            // set up margins for each TextView in the table layout
            android.widget.TableRow.LayoutParams layoutParams =
                    new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT );
            layoutParams.setMargins(20, 0, 20, 0);

            // read the CSV data
            CSVReader reader = new CSVReader( new InputStreamReader( in_s ) );
            String[] nextLine;
            while( ( nextLine = reader.readNext() ) != null ) {

                // nextLine[] is an array of values from the line

                // create the next table row for the layout
                TableRow tableRow = new TableRow( getBaseContext() );
                for( int i = 0; i < nextLine.length; i++ ) {

                    // create a new TextView and set its text
                    TextView textView = new TextView( getBaseContext() );
                    // for all columns exept the SCHOOL, align right
                    if( i != 1 )
                        textView.setGravity(Gravity.RIGHT);
                    textView.setText( nextLine[i] );

                    // add the new TextView to the table row in the table supplying the
                    // layout parameters
                    tableRow.addView( textView, layoutParams );
                }

                // add the next row to the table layout
                tableLayout.addView( tableRow );
            }
        } catch (Exception e) {
            Log.e( TAG, e.toString() );
        }
        //end of csv transfer
    }

    // We should override onUpgrade method, which will be used to upgrade the database if
    // its version (DB_VERSION) has changed.  This will be done automatically by Android
    // if the version will be bumped up, as we modify the database schema.
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_STATEQUIZ );
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_STATEQUIZ + " upgraded" );
    }
}
