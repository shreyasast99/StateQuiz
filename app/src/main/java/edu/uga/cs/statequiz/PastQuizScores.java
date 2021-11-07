package edu.uga.cs.statequiz;

import static edu.uga.cs.statequiz.StateData.DEBUG_TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PastQuizScores extends AppCompatActivity {
    private RecyclerView recyclerView;
    //private JobLeadRecyclerAdapter recyclerAdapter;

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
        // Note that even though more activites may create their own instances of the JobLeadsData
        // class, we will be using a single instance of the JobLeadsDBHelper object, since
        // that class is a singleton class.
        // jobLeadsData = new JobLeadsData( this );

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
            StateData.open();
            stateList = StateData.retrieveAllJobLeads();

            Log.d( DEBUG_TAG, "JobLeadDBReaderTask: Job leads retrieved: " + stateList.size() );

            return stateList;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( List<State> jobLeadsList ) {
            recyclerAdapter = new JobLeadRecyclerAdapter( jobLeadsList );
            recyclerView.setAdapter( recyclerAdapter );
        }
    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing of a job lead, asynchronously.
    public class JobLeadDBWriter extends AsyncTask<State, State> {

        // This method will run as a background process to write into db.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onClick listener of the Save button.
        @Override
        protected State doInBackground( State... states ) {
            statesData.storeJobLead( states[0] );
            return states[0];
        }

        // This method will be automatically called by Android once the writing to the database
        // in a background process has finished.  Note that doInBackground returns a JobLead object.
        // That object will be passed as argument to onPostExecute.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( State state ) {
            // Update the recycler view to include the new job lead
            stateList.add( state );
            recyclerAdapter.notifyItemInserted(stateList.size() - 1);

            Log.d( DEBUG_TAG, "Job lead saved: " + state );
        }
    }

    // this is our own callback for a DialogFragment which adds a new job lead.
    public void onFinishNewJobLeadDialog(State state) {
        // add the new job lead
        new JobLeadDBWriter().execute( state );
    }

    void showDialogFragment( DialogFragment newFragment ) {
        newFragment.show( getSupportFragmentManager(), null);
    }
}
