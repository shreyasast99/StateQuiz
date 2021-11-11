package edu.uga.cs.statequiz;

import static edu.uga.cs.statequiz.StateData.DEBUG_TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * This class displays the past quiz scores
 * */
public class PastQuizScores extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StateRecyclerAdapter recyclerAdapter;

    private StateData statesData = null;
    private List<Score> scoreList;

    /**
     * @param savedInstanceState
     * @return
     * This method creates the list of past quiz scores.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_quiz_scores);
        recyclerView = findViewById( R.id.recyclerView );

        // use a linear layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Create a JobLeadsData instance, since we will need to save a new JobLead to the dn.
        // Note that even though more activities may create their own instances of the JobLeadsData
        // class, we will be using a single instance of the JobLeadsDBHelper object, since
        // that class is a singleton class.
        statesData = new StateData( this );

        // Execute the retrieval of the job leads in an asynchronous way,
        // without blocking the main UI thread.
        new StateQuizDBHelper().execute();
    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB reading of job leads, asynchronously.
    /**
     * This class helps with the database
     * */
    private class StateQuizDBHelper extends AsyncTask<Void, List<Score>> {
        // This method will run as a background process to read from db.
        // It returns a list of retrieved JobLead objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the job leads review activity is started).
        /**
         * @param params
         * This method runs in the background to read data and score objects.
         * */
        @Override
        protected List<Score> doInBackground( Void... params ) {
            statesData.open();
            scoreList = statesData.retrieveAllScores();

            Log.d( DEBUG_TAG, "JobLeadDBReaderTask: Job leads retrieved: " + scoreList.size() );

            return scoreList;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        /**
         * @param jobLeadsList
         * This method will create and set the adapter to provide the information needed for the Recycler View/
         * */
        @Override
        protected void onPostExecute( List<Score> jobLeadsList ) {
            recyclerAdapter = new StateRecyclerAdapter( jobLeadsList );
            recyclerView.setAdapter( recyclerAdapter );
        }
    }
}
