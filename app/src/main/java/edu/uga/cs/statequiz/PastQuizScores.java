package edu.uga.cs.statequiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PastQuizScores extends AppCompatActivity {
    private RecyclerView recyclerView;
    //private JobLeadRecyclerAdapter recyclerAdapter;

    //private JobLeadsData jobLeadsData = null;
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
}
