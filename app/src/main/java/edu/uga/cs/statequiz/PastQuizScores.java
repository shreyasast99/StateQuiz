package edu.uga.cs.statequiz;

import static edu.uga.cs.statequiz.StateData.DEBUG_TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PastQuizScores extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StateRecyclerAdapter recyclerAdapter;

    private StateData statesData = null;
    private List<State> stateList;
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
    private class StateQuizDBHelper extends AsyncTask<Void, List<State>> {
        // This method will run as a background process to read from db.
        // It returns a list of retrieved JobLead objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the job leads review activity is started).
        @Override
        protected List<State> doInBackground( Void... params ) {
            statesData.open();
            stateList = statesData.retrieveAllJobLeads();

            Log.d( DEBUG_TAG, "JobLeadDBReaderTask: Job leads retrieved: " + stateList.size() );

            return stateList;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( List<State> jobLeadsList ) {
            recyclerAdapter = new StateRecyclerAdapter( jobLeadsList );
            recyclerView.setAdapter( recyclerAdapter );
        }
    }
}
