package edu.uga.cs.statequiz;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is facilitates storing and restoring job leads stored.
 */
public class StateData {

    public static final String DEBUG_TAG = "JobLeadsData";

    // this is a reference to our database; it is used later to run SQL commands
    private SQLiteDatabase   db;
    private SQLiteOpenHelper stateQuizDbHelper;
    private static final String[] allColumns = {
            StateQuizDBHelper.STATEQUIZ_COLUMN_ID,
            StateQuizDBHelper.STATEQUIZ_COLUMN_STATE,
            StateQuizDBHelper.STATEQUIZ_COLUMN_CAPITAL,
            StateQuizDBHelper.STATEQUIZ_COLUMN_SECONDCITY,
            StateQuizDBHelper.STATEQUIZ_COLUMN_THIRDCITY
            //StateQuizDBHelper.STATEQUIZ_COLUMN_STATEHOOD,
            //StateQuizDBHelper.STATEQUIZ_COLUMN_CAPITALSINCE,
            //StateQuizDBHelper.STATEQUIZ_COLUMN_SIZERANK
    };

    public StateData( Context context ) {
        this.stateQuizDbHelper = StateQuizDBHelper.getInstance( context );
    }

    // Open the database
    public void open() {
        db = stateQuizDbHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "JobLeadsData: db open" );
    }

    // Close the database
    public void close() {
        if( stateQuizDbHelper != null ) {
            stateQuizDbHelper.close();
            Log.d(DEBUG_TAG, "JobLeadsData: db closed");
        }
    }

    // Retrieve all job leads and return them as a List.
    // This is how we restore persistent objects stored as rows in the job leads table in the database.
    // For each retrieved row, we create a new JobLead (Java POJO object) instance and add it to the list.
    public List<State> retrieveAllJobLeads() {
        ArrayList<State> stateList = new ArrayList<>();
        Cursor cursor = null;


        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( StateQuizDBHelper.TABLE_STATEQUIZ, allColumns,
                    null, null, null, null, null );
            Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );

            // collect all job leads into a List
            if( cursor.getCount() > 0 ) {
                while( cursor.moveToNext() ) {
                    // get all attribute values of this job lead
                    long id = cursor.getLong( cursor.getColumnIndex( StateQuizDBHelper.STATEQUIZ_COLUMN_ID ) );
                    String state = cursor.getString( cursor.getColumnIndex( StateQuizDBHelper.STATEQUIZ_COLUMN_STATE ) );
                    String capital = cursor.getString( cursor.getColumnIndex( StateQuizDBHelper.STATEQUIZ_COLUMN_CAPITAL ) );
                    String secondCity = cursor.getString( cursor.getColumnIndex( StateQuizDBHelper.STATEQUIZ_COLUMN_SECONDCITY ) );
                    String thirdCity = cursor.getString( cursor.getColumnIndex( StateQuizDBHelper.STATEQUIZ_COLUMN_THIRDCITY ) );
                    //String statehood = cursor.getString( cursor.getColumnIndex( StateQuizDBHelper.STATEQUIZ_COLUMN_STATEHOOD ) );
                    //String capitalSince = cursor.getString( cursor.getColumnIndex( StateQuizDBHelper.STATEQUIZ_COLUMN_CAPITALSINCE ) );
                    //String sizeRank = cursor.getString( cursor.getColumnIndex( StateQuizDBHelper.STATEQUIZ_COLUMN_SIZERANK ) );
                    // create a new JobLead object and set its state to the retrieved values
                    State stateObj = new State( state, capital, secondCity, thirdCity );
                    stateObj.setId( id ); // set the id (the primary key) of this object
                    // add it to the list
                    stateList.add( stateObj );
                    Log.d( DEBUG_TAG, "Retrieved JobLead: " + stateObj );
                }
            }
            Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // return a list of retrieved job leads
        return stateList;
    }
    //WE NEED TO CHANGE THIS FOR THE QUIZ INFO
    // Store a new job lead in the database
    public State storeJobLead( State jobLead ) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the JobLead argument.
        // This is how we are providing persistence to a JobLead (Java object) instance
        // by storing it as a new row in the database table representing job leads.
        ContentValues values = new ContentValues();
        //values.put( StateQuizDBHelper.STATEQUIZ_COLUMN_ID, jobLead.getId());
        values.put( StateQuizDBHelper.STATEQUIZ_COLUMN_STATE, jobLead.getState() );
        values.put( StateQuizDBHelper.STATEQUIZ_COLUMN_CAPITAL, jobLead.getCapital() );
        values.put( StateQuizDBHelper.STATEQUIZ_COLUMN_SECONDCITY, jobLead.getSecondCity() );
        values.put( StateQuizDBHelper.STATEQUIZ_COLUMN_THIRDCITY, jobLead.getThirdCity() );
        //values.put( StateQuizDBHelper.STATEQUIZ_COLUMN_STATEHOOD, jobLead.getStateHood() );
        //values.put( StateQuizDBHelper.STATEQUIZ_COLUMN_CAPITALSINCE, jobLead.getCapitalSince() );
        //values.put( StateQuizDBHelper.STATEQUIZ_COLUMN_SIZERANK, jobLead.getSizeRank() );

        // Insert the new row into the database table;
        // The id (primary key) is automatically generated by the database system
        // and returned as from the insert method call.
        long id = db.insert( StateQuizDBHelper.TABLE_STATEQUIZ, null, values );
        Log.d( DEBUG_TAG, "this the id bae: " + id );
        // store the id (the primary key) in the JobLead instance, as it is now persistent
        jobLead.setId( id );

        Log.d( DEBUG_TAG, "Stored new job lead with id: " + String.valueOf( jobLead.getId() ) );

        return jobLead;
    }


}
