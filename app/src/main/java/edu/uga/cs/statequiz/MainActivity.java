package edu.uga.cs.statequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class is the main activity/splash home page.
 * */
public class MainActivity extends AppCompatActivity {
    public static final String DEBUG_TAG = "NewJobLeadActivity";
    final String TAG = "CSVReading";
    private Button buttonNewQuiz;
    private StateData jobLeadsData = null;
    private Button buttonPastQuiz;

    /**
     * @param savedInstanceState
     * This creates the page
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonNewQuiz = findViewById(R.id.newQuiz);
        buttonNewQuiz.setOnClickListener(new ButtonClickListener());
        jobLeadsData = new StateData( this );
        buttonPastQuiz = findViewById(R.id.pastResults);
        buttonPastQuiz.setOnClickListener(new ButtonClickListener());

    }

    /**
     * This class is for the buttons
     * */
    private class ButtonClickListener implements View.OnClickListener {
        Intent intent;
        /**
         * Returns nothing.
         * Takes in Bundle as parameter.
         * This method performs a setup including layout creation and
         * data binding.
         */
        @Override
        public void onClick(View view) {

            try {
                // Open the CSV data file in the assets folder
                InputStream in_s = getAssets().open("state_capitals.csv" );

                // read the CSV data
                CSVReader reader = new CSVReader( new InputStreamReader( in_s ) );
                String[] nextLine;
                //Context context = null;


                while( ( nextLine = reader.readNext() ) != null ) {
                    State row = new State(nextLine[0],nextLine[1], nextLine[2], nextLine[3]);
                    new StateDBWriter().execute( row );
                    Log.e( TAG, nextLine[0] );
                    //jobLeadsData.storeJobLead(row);
                    // nextLine[] is an array of values from the line

                }


            } catch (Exception e) {
                Log.e( TAG, e.toString() );

            }

            switch (view.getId()) {
                case R.id.newQuiz:
                    intent = new Intent(view.getContext(), NewQuizActivity.class);
                    break;
                case R.id.pastResults:
                    intent = new Intent(view.getContext(), PastQuizScores.class);
                    Log.d( DEBUG_TAG, "HELLLLLO" );
                    break;
            }
            startActivity(intent);
        }


    }

    /**
     * This class is to write the data into the database
     * */
    public class StateDBWriter extends AsyncTask<State, State> {

        // This method will run as a background process to write into db.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onClick listener of the Save button.
        /**@param jobLeads*/
        @Override
        protected State doInBackground( State... jobLeads ) {
            jobLeadsData.storeJobLead( jobLeads[0] );
            return jobLeads[0];
        }

        // This method will be automatically called by Android once the writing to the database
        // in a background process has finished.  Note that doInBackground returns a JobLead object.
        // That object will be passed as argument to onPostExecute.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        /**@param jobLead*/
        @Override
        protected void onPostExecute( State jobLead ) {
            // Show a quick confirmation message
            Toast.makeText( getApplicationContext(), "Job lead created for " ,
                    Toast.LENGTH_SHORT).show();



            Log.d( DEBUG_TAG, "Job lead saved: " + jobLead );
        }
    }
    /**This is to resume the activity*/
    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onResume()" );
        // open the database in onResume
        if( jobLeadsData != null )
            jobLeadsData.open();
        super.onResume();
    }

    /**This is to pause the activity*/
    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onPause()" );
        // close the database in onPause
        if( jobLeadsData != null )
            jobLeadsData.close();
        super.onPause();
    }
}